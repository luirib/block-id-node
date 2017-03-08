package models.bigchaindb;

import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("bitmap")
    private int bitmap = 32;
    @SerializedName("signature")
    private String signature;
    @SerializedName("type")
    private String type = "fulfillment";
    @SerializedName("type_id")
    private int typeId = 4;

    public int getBitmap() {
        return bitmap;
    }

    public void setBitmap(int bitmap) {
        this.bitmap = bitmap;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

}
