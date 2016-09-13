package com.ingeniartesoft.siiom.io;

import android.util.Log;

import retrofit.RestAdapter;

/**
 * Created by german on 13/09/16.
 */
public class ApiAdapter {

    private static DiscipulosService API_SERVICE;

    public static DiscipulosService getApiService() {
        if (API_SERVICE == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("http://10.0.2.2:7000")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            API_SERVICE = adapter.create(DiscipulosService.class);
        }
        Log.d("P", "PASE POR AQUI");
        return API_SERVICE;
    }
}
