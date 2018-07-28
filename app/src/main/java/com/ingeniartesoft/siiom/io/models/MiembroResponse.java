package com.ingeniartesoft.siiom.io.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by german on 14/09/16.
 */
public class MiembroResponse extends ResponseCodeMixing {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("primer_apellido")
    private String primer_apellido;

    @SerializedName("segundo_apellido")
    private String segundo_apellido;

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("lideres")
    private String lideres;

    @SerializedName("grupo_lidera")
    private String grupo_lidera;

    @SerializedName("grupo_nombre")
    private String grupo_nombre;

    @SerializedName("foto_perfil")
    private String foto_perfil;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("email")
    private String email;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("genero")
    private String genero;


    public MiembroResponse(int response_code, String nombre, String primer_apellido, String segundo_apellido, String cedula, String lideres, String grupo_lidera, String grupo_nombre, String foto_perfil, String direccion, String email, String telefono, String genero) {
        super(response_code);
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.cedula = cedula;
        this.lideres = lideres;
        this.grupo_lidera = grupo_lidera;
        this.grupo_nombre = grupo_nombre;
        this.foto_perfil = foto_perfil;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
    }

    public MiembroResponse(int response_code, String message) {
        super(response_code, message);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getLideres() {
        return lideres;
    }

    public void setLideres(String lideres) {
        this.lideres = lideres;
    }

    public String getGrupo_lidera() {
        return grupo_lidera;
    }

    public void setGrupo_lidera(String grupo_lidera) {
        this.grupo_lidera = grupo_lidera;
    }

    public String getGrupo_nombre() {
        return grupo_nombre;
    }

    public void setGrupo_nombre(String grupo_nombre) {
        this.grupo_nombre = grupo_nombre;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
