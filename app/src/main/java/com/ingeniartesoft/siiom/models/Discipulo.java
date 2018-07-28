package com.ingeniartesoft.siiom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by german on 12/09/16.
 */
public class Discipulo {
    @SerializedName("imagen")
    private String imagen;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("_id")
    private int id;

    public Discipulo(String imagen, String nombre, int id) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }
}
