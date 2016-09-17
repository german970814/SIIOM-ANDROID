package com.ingeniartesoft.siiom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.commucator.Communicator;
import com.ingeniartesoft.siiom.io.ApiAdapter;
import com.ingeniartesoft.siiom.io.ServiceAPI;
import com.ingeniartesoft.siiom.io.models.LoginResponse;
import com.ingeniartesoft.siiom.models.User;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.ingeniartesoft.siiom.ui.Constants;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements Callback<LoginResponse> {

    private String email, password;
    private EditText emailET, passwordET;
    private Button loginButtonGet;
    private FloatingActionButton loginButtonPost;
    private TextView information, extraiformation;
    private final static String TAG = "LoginActivity";
    ProgressDialog progressDialog;

    CardView card;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        card = (CardView) findViewById(R.id.card_view);
        // card.setVisibility(View.GONE);
        passwordET.setTransformationMethod(new PasswordTransformationMethod());

        loginButtonPost = (FloatingActionButton) findViewById(R.id.button_login);
        loginButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                ApiAdapter.getApiService().login(email, password, LoginActivity.this);
                // usePost(email, password);
                // progressDialog = ProgressDialog.show(LoginActivity.this, "", "Espera por favor...", true);
                // progressDialog.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                card.setVisibility(View.GONE);
                loginButtonPost.setVisibility(View.GONE);
            }
        });

        passwordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });

        emailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });

        information = (TextView) findViewById(R.id.information);
    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

//    @Subscribe
//    public void onServerEvent(ServerEvent serverEvent) {
//        Toast.makeText(this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
//        if (serverEvent.getServerResponse().getEmail() != null && serverEvent.getServerResponse().getPassword() != null) {
//            // information.setText("Username: " +  serverEvent.getServerResponse().getEmail() + " || Password: " + serverEvent.getServerResponse().getPassword());
//
//            ServerResponse serverResponse = serverEvent.getServerResponse();
//
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("MIEMBRO_ID", serverResponse.getResponseId());
//            // progressDialog.dismiss();
//            startActivity(intent);
//        }
//        // information.setText("" + serverEvent.getServerResponse().getMessage());
//    }
//
//    @Subscribe
//    public void onErrorEvent(ErrorEvent errorEvent) {
//        Toast.makeText(this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
//        // progressDialog.dismiss();
//        progressBar.setVisibility(View.GONE);
//        card.setVisibility(View.VISIBLE);
//        loginButtonPost.setVisibility(View.VISIBLE);
//    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void success(LoginResponse serverResponse, Response response) {
        if (serverResponse.getResponse_code() == Constants.SUCCESS) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("MIEMBRO_ID", serverResponse.getID_MIEMBRO());
            startActivity(intent);
        } else {
            Toast.makeText(this, "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            card.setVisibility(View.GONE);
            loginButtonPost.setVisibility(View.GONE);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("E", "error desde retrofit en miembro");
        error.printStackTrace();
    }
}
