package com.ingeniartesoft.siiom.commucator;

import com.ingeniartesoft.siiom.server.ServerResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by german on 9/08/16.
 */
public interface Interface {

    // This method is used for "POST"
    // Acceder al servicio
    @FormUrlEncoded
    @POST("/login/")
    void loginPost(@Field("email") String email,
                   @Field("password") String password,
                   Callback<ServerResponse> serverResponseCallback);

    //This method is used for "GET"
    @GET("/login/")
    void getData(@Query("method") String method,
                 @Query("email") String email,
                 @Query("password") String password,
                 Callback<ServerResponse> serverResponseCallback);


    @GET("/miembro/{id_miembro}")
    void getGrupo(@Path("id_miembro") int id,
                  Callback<ServerResponse> serverResponseCallback);

    @FormUrlEncoded
    @POST("/editar_miembro/{id_miembro}")
    void editarMiembroPost(@Path("id_miembro") int id, @Field("nombre") String nombre,
                           @Field("email") String email, @Field("primer_apellido") String primer_apellido,
                           @Field("segundo_apellido") String segundo_apellido, @Field("direccion") String direccion,
                           @Field("telefono") String telefono, @Field("cedula") String cedula,
                           Callback<ServerResponse> serverResponseCallback);

    @FormUrlEncoded
    @POST("/editar_grupo/{id_miembro}")
    void editarGrupoPost(@Path("id_miembro") int id,
                         @Field("direccion") String direccion,
                         @Field("dia") String dia, @Field("hora") String hora,
                         Callback<ServerResponse> serverResponseCallback);

}
