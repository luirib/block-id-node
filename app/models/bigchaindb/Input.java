package models.bigchaindb;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Input {

    @SerializedName("owners_before")
    private String previousOwners[];
    @SerializedName("fulfillment")
    private String fulfillment;
    @SerializedName("fulfills")
    private JsonObject outputCondition;

    public String[] getPreviousOwners() {
        return previousOwners;
    }

    public void setPreviousOwners(String[] previousOwners) {
        this.previousOwners = previousOwners;
    }

    public String getFullfillment() {
        return fulfillment;
    }

    public void setFullfillment(String fullfillment) {
        this.fulfillment = fullfillment;
    }

    public JsonObject getOutputCondition() {
        return outputCondition;
    }

    public void setOutputCondition(JsonObject outputCondition) {
        this.outputCondition = outputCondition;
    }
}
