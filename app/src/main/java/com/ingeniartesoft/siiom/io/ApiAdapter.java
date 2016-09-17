package com.ingeniartesoft.siiom.io;

import com.ingeniartesoft.siiom.ui.Constants;

import retrofit.RestAdapter;

/**
 * Created by german on 13/09/16.
 */
public class ApiAdapter {

    private static ServiceAPI API_SERVICE;

    public static ServiceAPI getApiService() {
        if (API_SERVICE == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(Constants.URL_BASE)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            API_SERVICE = adapter.create(ServiceAPI.class);
        }
        return API_SERVICE;
    }
}
