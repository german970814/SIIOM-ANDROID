package com.ingeniartesoft.siiom.io.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by german on 15/09/16.
 */
public class ResponseCodeMixing {
    @SerializedName("message")
    private String message;

    @SerializedName("response_code")
    private int response_code;

    public ResponseCodeMixing(int response_code) {
        this.response_code = response_code;
    }

    public ResponseCodeMixing(int response_code, String message) {
        this.response_code = response_code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }
}
