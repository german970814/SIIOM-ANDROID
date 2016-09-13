package com.ingeniartesoft.siiom.io;

import com.ingeniartesoft.siiom.io.models.DiscipulosResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by german on 13/09/16.
 */
public interface DiscipulosService {
    @GET("/api_v1/discipulos/{id_miembro}")
    void get_discipulos(@Path("id_miembro") int id_miembro, Callback<DiscipulosResponse> serverResponse);
}
