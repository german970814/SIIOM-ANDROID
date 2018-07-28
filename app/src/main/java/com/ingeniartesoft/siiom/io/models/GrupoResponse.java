package com.ingeniartesoft.siiom.io.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by german on 14/09/16.
 */
public class GrupoResponse extends ResponseCodeMixing {
    @SerializedName("nombre")
    private String nombre;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("lider1")
    private String lider1;

    @SerializedName("lider2")
    private String lider2;

    @SerializedName("dia_gar")
    private String dia_grupo;

    @SerializedName("hora_gar")
    private String hora_grupo;

    @SerializedName("estado")
    private String estado;

    public GrupoResponse(String estado, String hora_grupo, String dia_grupo, String lider2, String lider1, String direccion, String nombre, int responseCode) {
        super(responseCode);
        this.estado = estado;
        this.hora_grupo = hora_grupo;
        this.dia_grupo = dia_grupo;
        this.lider2 = lider2;
        this.lider1 = lider1;
        this.direccion = direccion;
        this.nombre = nombre;
    }

    public GrupoResponse(int responseCode, String message) {
        super(responseCode, message);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLider1() {
        return lider1;
    }

    public void setLider1(String lider1) {
        this.lider1 = lider1;
    }

    public String getLider2() {
        return lider2;
    }

    public void setLider2(String lider2) {
        this.lider2 = lider2;
    }

    public String getDia_grupo() {
        return dia_grupo;
    }

    public void setDia_grupo(String dia_grupo) {
        this.dia_grupo = dia_grupo;
    }

    public String getHora_grupo() {
        return hora_grupo;
    }

    public void setHora_grupo(String hora_grupo) {
        this.hora_grupo = hora_grupo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
