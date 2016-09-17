package com.ingeniartesoft.siiom.io.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by german on 15/09/16.
 */
public class LoginResponse extends ResponseCodeMixing {
    @SerializedName("ID_MIEMBRO")
    private int ID_MIEMBRO;


    public LoginResponse(int response_code, int ID_MIEMBRO) {
        super(response_code);
        this.ID_MIEMBRO = ID_MIEMBRO;
    }

    public LoginResponse(int response_code, String message) {
        super(response_code, message);
    }

    public int getID_MIEMBRO() {
        return ID_MIEMBRO;
    }

    public void setID_MIEMBRO(int ID_MIEMBRO) {
        this.ID_MIEMBRO = ID_MIEMBRO;
    }

}
