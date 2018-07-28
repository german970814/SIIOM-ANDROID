package com.ingeniartesoft.siiom.models;

/**
 * Created by german on 8/08/16.
 */
public class User {
    private int id;
    private String email;

    public User (int id, String email) {
        this.id = id;
        this.email = email;
    }

    public void setId (int value) {
        this.id = value;
    }
    public int getId () {
        return this.id;
    }

    public void setEmail (String value) {
        this.email = value;
    }
    public String getEmail() {
        return this.email;
    }


}
