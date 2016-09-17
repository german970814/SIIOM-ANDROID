package com.ingeniartesoft.siiom.io;

import com.ingeniartesoft.siiom.io.models.DiscipulosResponse;
import com.ingeniartesoft.siiom.io.models.GrupoResponse;
import com.ingeniartesoft.siiom.io.models.LoginResponse;
import com.ingeniartesoft.siiom.io.models.MiembroResponse;
import com.ingeniartesoft.siiom.ui.Constants;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by german on 13/09/16.
 */
public interface ServiceAPI {
    @FormUrlEncoded
    @POST(Constants.API_PATH + "/login/")
    void login(@Field("email") String email, @Field("password") String password, Callback<LoginResponse> serverResponse);

    @GET(Constants.API_PATH + "/miembro/{id_miembro}/")
    void get_miembro(@Path("id_miembro") int id_miembro, Callback<MiembroResponse> serverResponse);

    @FormUrlEncoded
    @POST(Constants.API_PATH + "/editar_miembro/{id_miembro}/")
    void editar_miembro(@Path("id_miembro") int id_miembro, @Field("nombre") String nombre,
                        @Field("primerApellido") String primerApellido, @Field("segundoApellido") String segundoApellido,
                        @Field("cedula") String cedula, @Field("email") String email, @Field("telefono") String telefono,
                        @Field("direccion") String direccion, Callback<MiembroResponse> serverResponse);

    @GET(Constants.API_PATH + "/grupo/{id_miembro}/")
    void get_grupo(@Path("id_miembro") int id_miembro, Callback<GrupoResponse> serverResponse);

    @FormUrlEncoded
    @POST(Constants.API_PATH + "/editar_grupo/{id_miembro}/")
    void editar_grupo(@Path("id_miembro") int id_miembro, @Field("diaGAR") String diaGAR,
                      @Field("horaGAR") String horaGAR, @Field("direccion") String direccion,
                      Callback<GrupoResponse> serverResponse);

    @GET(Constants.API_PATH + "/discipulos/{id_miembro}/")
    void get_discipulos(@Path("id_miembro") int id_miembro, Callback<DiscipulosResponse> serverResponse);
}
