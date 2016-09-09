package com.ingeniartesoft.siiom.models;

/**
 * Created by german on 28/08/16.
 */
public class Grupo {
    public int id;
    public String nombre;
    public Miembro lider1;
    public Miembro lider2;
    public String fecha_grupo;
    public String direccion_grupo;

    // Constructor
    public Grupo(int id, String nombre, Miembro lider1, Miembro lider2, String fecha_grupo, String direccion_grupo) {
        this.id = id;
        this.nombre = nombre;
        this.lider1 = lider1;
        this.lider2 = lider2;
        this.fecha_grupo = fecha_grupo;
        this.direccion_grupo = direccion_grupo;
    }
}
