package myshop.com.myshop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Respuesta implements Serializable {

    @SerializedName("responseCode")
    private int responseCode;

    @SerializedName("responseMessage")
    private String responseMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
