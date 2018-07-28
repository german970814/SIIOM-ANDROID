package com.ingeniartesoft.siiom.models;

import android.provider.ContactsContract;

/**
 * Created by german on 28/08/16.
 */
public class Miembro {
    public String nombre;
    public String primer_apellido;
    public String segundo_apellido;
    public String cedula;
    public User usuario;
    public int id;

    // Constructor
    public Miembro (int id, String nombre, String primer_apellido, String segundo_apellido, String cedula, User usuario) {
        this.id = id;
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.cedula = cedula;
        this.usuario = usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public User getUsuario() {
        return usuario;
    }

    public int getId() {
        return id;
    }
}
