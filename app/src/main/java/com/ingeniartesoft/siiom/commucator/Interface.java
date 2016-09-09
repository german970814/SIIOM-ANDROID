package com.ingeniartesoft.siiom.commucator;

import com.ingeniartesoft.siiom.server.ServerResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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


    @GET("/miembro/")
    void getGrupo(@Query("id_miembro") int id,
                  Callback<ServerResponse> serverResponseCallback);
}
