package models.bigchaindb;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    public enum Operation {
        CREATE, TRANSFER, GENESIS;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("version")
    private String version;
    @SerializedName("inputs")
    private Input[] inputs;
    @SerializedName("outputs")
    private Output[] outputs;
    @SerializedName("operation")
    private Operation operation;
    @SerializedName("asset")
    private Asset asset;
    @SerializedName("metadata")
    private JsonObject metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public void setInputs(Input[] inputs) {
        this.inputs = inputs;
    }

    public Output[] getOutputs() {
        return outputs;
    }

    public void setOutputs(Output[] outputs) {
        this.outputs = outputs;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public JsonObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JsonObject metadata) {
        this.metadata = metadata;
    }


}
