package com.ingeniartesoft.siiom.server;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by german on 9/08/16.
 */
public class ServerResponse implements Serializable {
        // login variables
        @SerializedName("returned_email")
        private String email;
        @SerializedName("returned_password")
        private String password;
        @SerializedName("message")
        private String message;
        @SerializedName("response_code")
        private int responseCode;
        @SerializedName("response_id")
        private int responseId;


        // variables de grupo
        @SerializedName("miembro")
        private String miembro;

        @SerializedName("grupo_lidera")
        private String grupo_lidera;

        @SerializedName("grupo_nombre")
        private String grupo_nombre;

        @SerializedName("grupo_id")
        private int grupo_id;

        @SerializedName("grupo_lider1")
        private String grupo_lider1;

        @SerializedName("grupo_lider2")
        private String grupo_lider2;

        @SerializedName("dia_grupo")
        private String dia_grupo;

        @SerializedName("direccion_grupo")
        private String direccion_grupo;

        @SerializedName("miembro_foto_perfil")
        private String foto_perfil;

        @SerializedName("cedula")
        private String cedula;

        @SerializedName("genero")
        private String genero;

        @SerializedName("direccion")
        private String direccion;

        @SerializedName("telefono")
        private String telefono;

        @SerializedName("primer_apellido")
        private String primer_apellido;

        @SerializedName("segundo_apellido")
        private String segundo_apellido;

        @SerializedName("lideres")
        private String lideres;

        @SerializedName("email")
        private String email_miembro;

        // Constructor de login
        public ServerResponse(String email, String password, String message, int responseId, int responseCode) {
            this.email = email;
            this.password = password;
            this.message = message;
            this.responseCode = responseCode;
            this.responseId = responseId;
        }

        // Constructor de Miembro y Grupo
        public ServerResponse(String grupoName, int grupoId, String lider1,
                              String lider2, String fecha_grupo, String direccion_grupo, String grupo_lidera,
                              String miembro, String foto_perfil, String cedula, String genero, String message, int responseCode,
                              String direccion, String email, String telefono, String lideres) {
            this.grupo_nombre = grupoName;
            this.message = message;
            this.responseCode = responseCode;
            this.grupo_id = grupoId;
            this.grupo_lider1 = lider1;
            this.grupo_lider2 = lider2;
            this.dia_grupo = fecha_grupo;
            this.direccion_grupo = direccion_grupo;
            this.grupo_lidera = grupo_lidera;
            this.miembro = miembro;
            this.cedula = cedula;
            this.foto_perfil = foto_perfil;
            this.genero = genero;
            this.direccion = direccion;
            this.email_miembro = email;
            this.telefono = telefono;
            this.lideres = lideres;
        }

        public int getGrupo_id() {
            return grupo_id;
        }

        public String getGrupo_lider1() {
            return grupo_lider1;
        }

        public String getMiembro() { return miembro; }

        public String getGrupo_lidera() { return grupo_lidera; }

        public String getGrupo_lider2() {
            return grupo_lider2;
        }

        public String getDia_grupo() {
            return dia_grupo;
        }

        public String getDireccion_grupo() {
            return direccion_grupo;
        }

        public String getGrupo_nombre() {
            return grupo_nombre;
        }

        public String getMessage() {
            return message;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public int getResponseId() { return responseId; }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFoto_perfil() {
            return foto_perfil;
        }

        public String getCedula() {
            return cedula;
        }

        public String getTelefono() {
            return telefono;
        }

        public String getPrimer_apellido() {
            return primer_apellido;
        }

        public String getSegundo_apellido() {
            return segundo_apellido;
        }

        public String getDireccion() {
            return direccion;
        }

        public String getLideres() {
            return lideres;
        }

        public String getEmail_miembro() {
            return email_miembro;
        }
}
