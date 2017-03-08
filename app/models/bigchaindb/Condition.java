package models.bigchaindb;

import com.google.gson.annotations.SerializedName;

public class Condition {

    @SerializedName("details")
    private Details details;
    @SerializedName("uri")
    private String uri;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}