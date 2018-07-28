package com.ingeniartesoft.siiom;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.io.ApiAdapter;
import com.ingeniartesoft.siiom.io.models.MiembroResponse;
import com.ingeniartesoft.siiom.ui.Constants;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DiscipuloDetailActivity extends AppCompatActivity implements Callback<MiembroResponse> {
    private Toolbar toolbar;
    private Intent intent;
    static int DISCIPULO_ID;
    public static final String SIN_DATOS = "SIN DATOS";

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private TextView nNombre, nCedula, nDireccion, nTelefono, nSegundoApellido, nPrimerApellido, nEmail;
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipulo_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar_discipulos);

        showToolbar(toolbar, "", true);

        intent = getIntent();
        DISCIPULO_ID = intent.getIntExtra("DISCIPULO_ID", 1);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_discipulos);

        background = (ImageView) findViewById(R.id.discipulo_profile_photo);
        nNombre = (TextView) findViewById(R.id.nombre_discipulo);
        nPrimerApellido = (TextView) findViewById(R.id.primer_apellido_discipulo);
        nSegundoApellido = (TextView) findViewById(R.id.segundo_apellido_discipulo);
        nCedula = (TextView) findViewById(R.id.cedula_discipulo);
        nTelefono = (TextView) findViewById(R.id.telefono_discipulo);
        nDireccion = (TextView) findViewById(R.id.direccion_discipulo);
        nEmail = (TextView) findViewById(R.id.email_discipulo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiAdapter.getApiService().get_miembro_discipulo(DISCIPULO_ID, "true", this);
    }

    public void showToolbar(@NonNull Toolbar view, String title, boolean upButton) {
        setSupportActionBar(view);
        if (title != null && !title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
//        getSupportActionBar().set
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("F", "ENTRE EN ESTE**************");
                this.finish();
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void success(MiembroResponse serverResponse, Response response) {
        if (serverResponse.getResponse_code() == Constants.SUCCESS || serverResponse.getResponse_code() == Constants.CREATED) {
            if (!serverResponse.getNombre().isEmpty() && !serverResponse.getPrimer_apellido().isEmpty()) {
                String nombres = serverResponse.getNombre() + ' ' + serverResponse.getPrimer_apellido();
                collapsingToolbarLayout.setTitle(nombres);
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.white));
                collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
            }

            if (validate_string(serverResponse.getDireccion())) {
                nDireccion.setText(serverResponse.getDireccion());
            } else {
                nDireccion.setText(SIN_DATOS);
            }

            if (validate_string(serverResponse.getSegundo_apellido())) {
                nSegundoApellido.setText(serverResponse.getSegundo_apellido());
            } else {
                nSegundoApellido.setText(SIN_DATOS);
            }

            if (validate_string(serverResponse.getCedula())) {
                nCedula.setText(serverResponse.getCedula());
            } else {
                nCedula.setText(SIN_DATOS);
            }

            if (validate_string(serverResponse.getNombre())) {
                nNombre.setText(serverResponse.getNombre());
            } else {
                nNombre.setText(SIN_DATOS);
            }

            if (validate_string(serverResponse.getPrimer_apellido())) {
                nPrimerApellido.setText(serverResponse.getPrimer_apellido());
            } else {
                nPrimerApellido.setText(SIN_DATOS);
            }

            if (validate_string(serverResponse.getTelefono())) {
                nTelefono.setText(serverResponse.getTelefono());
            } else {
                nTelefono.setText(SIN_DATOS);
            }

            if (validate_string(serverResponse.getEmail())) {
                nEmail.setText(serverResponse.getEmail());
            } else {
                nEmail.setText(SIN_DATOS);
            }

            Picasso
                    .with(this)
                    .load(Constants.URL_BASE + serverResponse.getFoto_perfil())
                    .resize(1600,800)
                    .into(background);

        } else {
            Log.d("G", "NO FUE RESPUESSTAA 200");
        }

        // String URL2 = "/static/Imagenes/cdr.com.png";
        // Picasso.with(getActivity()).load(Constants.URL_BASE + URL2).into(background);

//        toggleProgressBar();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("F", "Error de Retrofit desde la vista de DiscipulosDetail");
    }

    public boolean validate_string(String data) {
        return data != null && !data.isEmpty();
    }
}
