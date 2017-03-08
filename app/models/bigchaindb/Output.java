package models.bigchaindb;

import com.google.gson.annotations.SerializedName;

public class Output {

    @SerializedName("amount")
    private Integer amount;
    @SerializedName("condition")
    private Condition condition;
    @SerializedName("public_keys")
    private String[] publicKeys;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String[] getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(String[] publicKeys) {
        this.publicKeys = publicKeys;
    }
}