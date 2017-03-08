package models;

/**
 * Created by luirib on 10/02/17.
 */
public class MasterKeyPart
{
    private String id;
    private String privateKeyPart;
    private String publicKey;

    public MasterKeyPart()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivateKeyPart() {
        return privateKeyPart;
    }

    public void setPrivateKeyPart(String privateKeyPart) {
        this.privateKeyPart = privateKeyPart;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MasterKeyPart that = (MasterKeyPart) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
