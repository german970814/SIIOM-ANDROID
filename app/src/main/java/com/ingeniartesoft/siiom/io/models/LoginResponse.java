package com.ingeniartesoft.siiom.io.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by german on 15/09/16.
 */
public class LoginResponse extends ResponseCodeMixing {
    @SerializedName("ID_MIEMBRO")
    private int ID_MIEMBRO;

    @SerializedName("fields_error")
    private ArrayList<String> error_fields;

    public LoginResponse(int response_code, int ID_MIEMBRO) {
        super(response_code);
        this.ID_MIEMBRO = ID_MIEMBRO;
    }

    public LoginResponse(int response_code, String message) {
        super(response_code, message);
    }

    public LoginResponse(int response_code, String message, ArrayList<String> error_fields) {
        super(response_code, message);
        this.error_fields = error_fields;
    }

    public ArrayList<String> getError_fields() {
        return error_fields;
    }

    public int getID_MIEMBRO() {
        return ID_MIEMBRO;
    }

    public void setID_MIEMBRO(int ID_MIEMBRO) {
        this.ID_MIEMBRO = ID_MIEMBRO;
    }

}
