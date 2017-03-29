package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;

import models.*;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.util.*;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

import sun.security.rsa.RSAPublicKeyImpl;

/**
 * Created by luirib on 10/02/17.
 */
public class NodesController extends Controller
{

    private static HashMap<String, MasterKeyPart> keyMap = new HashMap<>();
    private static HashMap<MasterKeyPart, List<String>> keyMapBlock = new HashMap<>();

    private static HashMap<String, SignedJWT> blockMap = new HashMap<>();

    private static HashMap<String, SignedJWT> assMap = new HashMap<>();

    private static HashMap<String, String> idps = new HashMap<>();

    static
    {
        idps.put("https://dev01.signicat.com/oidc","{\"keys\":[{\"kty\":\"RSA\",\"e\":\"AQAB\",\"use\":\"sig\",\"kid\":\"any.oidc.test.jwk.v.1\",\"alg\":\"RS256\",\"n\":\"xYJk-Zyh4A8lNme0hZaKBlMdsISFsOH5doPQg56EN7l1KXxXNJJfxzohbFklmgYCQQvPmXzpXDOVBWPDQDRs5uq8Tm4N55AiKgFp51FfIwn9rBXs4j3aXWi35743d1mAoVXJX0w2w3acu9ejUZTWmp8bQbFLkadu8TPobUQvsA3U-UUwrWjV1kLbkSYfwW7TU1rbeHB_edKKSfK4qj41T-6ntGdgWtpBpkl5NZqBWxEgzg3REjq98LOD0An-Dq56TLHEEay7xWIPMuMRu2qG0x3W1Rk_U8iem1qcPLf5MTBdNFlO38xTnjx80TD7qlPoMSX8KspsG5Qh86U3bn23Cw\"}]}");
    }

    public Result list()
    {
        Node[] nodes = {new Node("um")};
        return ok();
    }
    @Transactional
    @BodyParser.Of(value = BodyParser.Json.class)
    public Result saveMasterKey()
    {
        System.err.println("Receiving Master Key");
        JsonNode json = request().body().asJson();
        if (json == null)
        {
            return badRequest("Empty JSON object.");
        }
        MasterKeyPart key = fromJson(json, MasterKeyPart.class);

        if(key == null || key.getId().isEmpty())
        {
            return badRequest("Master key part bad formatted");
        }

        if(!keyMap.containsKey(key.getId()))
        {
            keyMap.put(key.getId(),key);
        }

        return noContent();
    }

    public Result getMasterKey(String id)
    {
        System.err.println("Getting Master Key "+id);
        if(!keyMap.containsKey(id))
        {
            return notFound();
        }
        MasterKeyPart key = keyMap.get(id);
        return ok(toJson(key));


    }


    @BodyParser.Of(value = BodyParser.Text.class)
    public Result saveBlock()
    {
        String data = request().body().asText();
        if (data == null)
        {
            return badRequest("Data not received");
        }

        try {
            final SignedJWT jwt = SignedJWT.parse(data);
            String keyId = jwt.getHeader().getKeyID();

            MasterKeyPart key = keyMap.get(jwt.getHeader().getKeyID());

            Base64URL base64URL = new Base64URL(key.getPublicKey());
            RSAPublicKeyImpl publicKey = new RSAPublicKeyImpl(base64URL.decode());

            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            if(!jwt.verify(verifier))
            {
                return unauthorized("The signature is not correct");
            }

            UUID blockId = UUID.randomUUID();

            if(!keyMapBlock.containsKey(key))
            {
                keyMapBlock.put(key, new ArrayList<String>());
            }
            keyMapBlock.get(key).add(blockId.toString());

            blockMap.put(blockId.toString(),jwt);

            System.err.println("Adding block:"+ blockId.toString());

            //and then finally
            return ok(blockId.toString());

        } catch (ParseException e) {
            return badRequest("Expecting a Signed ");
        } catch (InvalidKeyException e) {
            return badRequest(e.getMessage());
        } catch (JOSEException e) {
            return badRequest(e.getMessage());
        }
    }

    public Result getBlock(String id)
    {
        System.err.println("Get Block Id: "+id);
        if (id == null || id.isEmpty())
        {
            return badRequest("Empty Block Id.");
        }
        SignedJWT jwt = blockMap.get(id);

        return ok(jwt.serialize());
    }

    public Result listBlockIds(String keyId)
    {
        System.err.println("list Block Ids from keyId: "+ keyId);
        if (keyId == null || keyId.isEmpty())
        {
            return badRequest("Empty Key Id.");
        }
        MasterKeyPart received = new MasterKeyPart();
        received.setId(keyId);

        List<String> list = keyMapBlock.get(received);
        if(list == null)
        {
            //send empty if not found
            list = new ArrayList<>();
        }

        return ok(toJson(list));
    }


    @BodyParser.Of(value = BodyParser.Text.class)
    public Result saveAssertion()
    {
        System.err.println("Saving assertion");
        String data = request().body().asText();
        if (data == null)
        {
            return badRequest("Data not received");
        }

        try
        {
            final SignedJWT jwt = SignedJWT.parse(data);

            MasterKeyPart key = keyMap.get(jwt.getHeader().getKeyID());

            Base64URL base64URL = new Base64URL(key.getPublicKey());
            RSAPublicKeyImpl publicKey = new RSAPublicKeyImpl(base64URL.decode());

            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            if(!jwt.verify(verifier))
            {
                return unauthorized("The signature is not correct");
            }


            //TODO send to the BC

            assMap.put(jwt.getJWTClaimsSet().getJSONObjectClaim("payload").get("sub").toString(),jwt);

            UUID blockId = UUID.randomUUID();

            blockMap.put(blockId.toString(),jwt);

            if(!keyMapBlock.containsKey(key))
            {
                keyMapBlock.put(key, new ArrayList<String>());
            }
            keyMapBlock.get(key).add(blockId.toString());

            System.err.println("Adding block:"+ blockId.toString());

            //and then finally
            return ok();

        }
        catch (Exception e)
        {
            return badRequest(e.getMessage());
        }
    }

    @BodyParser.Of(value = BodyParser.Text.class)
    public Result getTradeAssertion()
    {
        System.err.println("get trade assertion");
        String data = request().body().asText();
        if (data == null)
        {
            return badRequest("Data not received");
        }

        try
        {
            final SignedJWT jwt = SignedJWT.parse(data);

            if(!idps.containsKey(jwt.getJWTClaimsSet().getIssuer().trim()))
            {
                return badRequest("Unknown IdP issuer.");
            }


            System.err.println(jwt.getJWTClaimsSet().getIssuer().trim());
            JWKSet keySet = JWKSet.parse(idps.get(jwt.getJWTClaimsSet().getIssuer().trim()));

            JWSVerifier verifier = new RSASSAVerifier((RSAKey) keySet.getKeys().get(0));

            if(!jwt.verify(verifier))
            {
                return unauthorized("The signature is not correct");
            }

            String subId = jwt.getJWTClaimsSet().getSubject();

            System.err.println("subId: "+subId);

            SignedJWT ass = assMap.get(subId);
            System.err.println(ass.serialize());

            String keyId = ass.getHeader().getKeyID();

            System.err.println("\tkeyId: "+ keyId);
            MasterKeyPart part = keyMap.get(keyId);

            JsonNode json = toJson(part);
            System.err.println("\tSent"+json);
            return ok(json);

        }
        catch (Exception e)
        {
            return badRequest(e.getMessage());
        }
    }

    private <T>T fromBase64(String b64String, Class<T> classe) throws IOException {
        String s = decode(b64String);
        return fromString(s,classe);
    }

    private <T>T fromString(String string, Class<T> classe) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode j =  mapper.readTree(string);
        T obj = fromJson(j, classe);
        return obj;
    }

    private String decode(String b64String)
    {
        Base64URL b64Url = new Base64URL(b64String);
        return b64Url.decodeToString();
    }

}
