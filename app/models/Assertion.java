package models;

/**
 * Created by luirib on 11/02/17.
 */
public class Assertion
{
    private Payload payload;
    private String signature;


    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
