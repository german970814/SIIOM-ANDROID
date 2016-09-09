package com.ingeniartesoft.siiom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.commucator.Communicator;
import com.ingeniartesoft.siiom.models.User;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class SecondActivity extends AppCompatActivity {

    private Communicator communicator;
    private String email, password;
    private EditText emailET, passwordET;
    private Button loginButtonGet;
    private FloatingActionButton loginButtonPost;
    private TextView information, extraiformation;
    private final static String TAG = "SecondActivity";
    public static Bus bus;
    ProgressDialog progressDialog;

    CardView card;
    ProgressBar progressBar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        communicator = new Communicator();

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
                usePost(email, password);
                // progressDialog = ProgressDialog.show(SecondActivity.this, "", "Espera por favor...", true);
                // progressDialog.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                card.setVisibility(View.GONE);
                loginButtonPost.setVisibility(View.GONE);
            }
        });

        information = (TextView) findViewById(R.id.information);
    }

    private void usePost(String email, String password) {
        communicator.loginPost(email, password);
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

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        Toast.makeText(this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        if (serverEvent.getServerResponse().getEmail() != null && serverEvent.getServerResponse().getPassword() != null) {
            // information.setText("Username: " +  serverEvent.getServerResponse().getEmail() + " || Password: " + serverEvent.getServerResponse().getPassword());

            ServerResponse serverResponse = serverEvent.getServerResponse();
            user = new User(serverResponse.getResponseId(), serverResponse.getEmail());

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("MIEMBRO_ID", serverResponse.getResponseId());
            // progressDialog.dismiss();
            startActivity(intent);
        }
        // information.setText("" + serverEvent.getServerResponse().getMessage());
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
        // progressDialog.dismiss();
        progressBar.setVisibility(View.GONE);
        card.setVisibility(View.VISIBLE);
        loginButtonPost.setVisibility(View.VISIBLE);
    }

}
