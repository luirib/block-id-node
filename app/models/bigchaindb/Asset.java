package models.bigchaindb;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Asset {

//    @SerializedName("id")
//    private String id;
    @SerializedName("data")
    private JsonObject data;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
