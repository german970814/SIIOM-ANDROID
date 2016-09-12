package com.ingeniartesoft.siiom.commucator;

import android.util.Log;

import com.ingeniartesoft.siiom.BusProvider;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.squareup.otto.Produce;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by german on 9/08/16.
 */
public class Communicator {
    public static final String TAG = "Communicator";
    public static final String SERVER_URL = "http://10.0.2.2:7000/api_v1";

    public void loginPost(String email, String password) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response) {
                if (serverResponse.getResponseCode() == 0) {
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200, error.getMessage()));
            }
        };

        communicatorInterface.loginPost(email, password, callback);
    }

    public void loginGet(String email, String password) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response) {
                if (serverResponse.getResponseCode() == 0) {
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200, error.getMessage()));
            }
        };
        communicatorInterface.loginPost(email, password, callback);
    }

    public void getGrupo(int id) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response) {
                if (serverResponse.getResponseCode() == 0) {
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200, error.getMessage()));
            }
        };

        communicatorInterface.getGrupo(id, callback);

    }

    public void editarMiembroPOST(int id, String nombre,
                                  String email, String primer_apellido,
                                  String segundo_apellido, String direccion,
                                  String telefono, String cedula) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response) {
                if (serverResponse.getResponseCode() == 0) {
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200, error.getMessage()));
            }
        };

        communicatorInterface.editarMiembroPost(
                id, nombre, email, primer_apellido,
                segundo_apellido, direccion, telefono, cedula, callback
        );
    }

    public void editarGrupoPost(int id, String direccion, String dia, String hora) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response) {
                if (serverResponse.getResponseCode() == 0) {
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200, error.getMessage()));
            }
        };

        communicatorInterface.editarGrupoPost(id, direccion, dia, hora, callback);
    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}
