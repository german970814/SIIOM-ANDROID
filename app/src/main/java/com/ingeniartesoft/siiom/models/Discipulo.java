package com.ingeniartesoft.siiom.models;

/**
 * Created by german on 12/09/16.
 */
public class Discipulo {
    private String imagen;
    private String nombre;

    public Discipulo(String imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }
}
