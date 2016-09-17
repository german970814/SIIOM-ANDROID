package com.ingeniartesoft.siiom;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.io.ApiAdapter;
import com.ingeniartesoft.siiom.io.models.MiembroResponse;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.ingeniartesoft.siiom.ui.Constants;
import com.squareup.picasso.Picasso;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by german on 28/08/16.
 */
public class MiembroFragment extends Fragment implements Callback<MiembroResponse> {
    public final String TAG = "MiembroFragment";
    private static final String SIN_DATOS = "SIN DATOS";
    private int ID_MIEMBRO;

    private TextView nombre_miembro_header, nombre_miembro, primer_apellido,
    segundo_apellido, telefono, direccion, cedula,
    email, lideres, biografia;
    private EditText nombre_miembro_post,  primer_apellido_post, segundo_apellido_post, telefono_post, direccion_post, email_post, cedula_post;
    private FloatingActionButton button_success;
    private ImageView image, background;

    private ImageButton edit;
    private ErrorEvent serverError;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);
        final View _v = v;
        ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", 1);

        // se inicializan las vistas

        // vistas de layout
        background = (ImageView) v.findViewById(R.id.header_cover_image);
        nombre_miembro_header = (TextView) v.findViewById(R.id.user_profile_name);
        biografia = (TextView) v.findViewById(R.id.user_profile_short_bio);
        button_success = (FloatingActionButton) v.findViewById(R.id.button_send_miembro);
        image = (ImageView) v.findViewById(R.id.user_profile_photo);
        edit = (ImageButton) v.findViewById(R.id.editar_button);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_tab1);
        scrollView = (ScrollView) v.findViewById(R.id.scrollview2);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        // vistas de texto miembro
        lideres = (TextView) v.findViewById(R.id.lideres);
        direccion = (TextView) v.findViewById(R.id.direccion);
        cedula = (TextView) v.findViewById(R.id.cedula);
        nombre_miembro = (TextView) v.findViewById(R.id.nombre_miembro);
        primer_apellido = (TextView) v.findViewById(R.id.primer_apellido);
        segundo_apellido = (TextView) v.findViewById(R.id.segundo_apellido);
        telefono = (TextView) v.findViewById(R.id.telefono);
        email = (TextView) v.findViewById(R.id.email_m);

        // vistas de formulario
        nombre_miembro_post = (EditText) v.findViewById(R.id.nombre_miembro_post);
        cedula_post = (EditText) v.findViewById(R.id.cedula_post);
        telefono_post = (EditText) v.findViewById(R.id.telefono_post);
        email_post = (EditText) v.findViewById(R.id.email_post);
        primer_apellido_post = (EditText) v.findViewById(R.id.primer_apellido_post);
        segundo_apellido_post = (EditText) v.findViewById(R.id.segundo_apellido_post);
        direccion_post = (EditText) v.findViewById(R.id.direccion_post);

        button_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // useGet(ID_MIEMBRO);
                ApiAdapter.getApiService().editar_miembro(
                        ID_MIEMBRO, nombre_miembro_post.getText().toString(), primer_apellido_post.getText().toString(),
                        segundo_apellido_post.getText().toString(), cedula_post.getText().toString(),
                        email_post.getText().toString(), telefono_post.getText().toString(), direccion_post.getText().toString()
                , MiembroFragment.this);
                ImageButton edit = (ImageButton) _v.findViewById(R.id.editar_button);
                edit.performClick();
                toggleProgressBar();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleView(nombre_miembro);
                toggleView(nombre_miembro_post);
                nombre_miembro_post.setText(nombre_miembro.getText());
                toggleView(primer_apellido);
                toggleView(primer_apellido_post);
                primer_apellido_post.setText(primer_apellido.getText());
                toggleView(segundo_apellido);
                toggleView(segundo_apellido_post);
                segundo_apellido_post.setText(segundo_apellido.getText());
                toggleView(cedula);
                toggleView(cedula_post);
                cedula_post.setText(cedula.getText());
                toggleView(telefono);
                toggleView(telefono_post);
                telefono_post.setText(telefono.getText());
                toggleView(email);
                toggleView(email_post);
                email_post.setText(email.getText());
                toggleView(direccion);
                toggleView(direccion_post);
                direccion_post.setText(direccion.getText());

                toggleView(button_success);

                if (button_success.getVisibility() == View.VISIBLE) {
                    edit.setRotationX(1.8f);
                    edit.animate().rotation(0.0f);
                    edit.setBackgroundResource(R.mipmap.ic_close_white_24dp); // setBackground(R.mipmap.ic_close_white_24dp);
                } else {
                    edit.setRotationX(1.8f);
                    edit.animate().rotation(0.0f);
                    edit.setBackgroundResource(R.mipmap.ic_create_white_24dp);
                }
            }
        });
        showToolbar(v, "Inicio", false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ApiAdapter.getApiService().get_miembro(ID_MIEMBRO, this);
    }

    public void showToolbar(@NonNull View view, String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.actiontoolbal);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
//        getA(toolbar);
//        getSupportActionBar();
    }

    public void setServerResponse (ServerResponse serverResponse, String URL_BASE) {
//        if (nombre_miembro_header != null && serverResponse.getMiembro() != null && !serverResponse.getMiembro().equals("")) {
//            nombre_miembro_header.setText(serverResponse.getMiembro() + " " + serverResponse.getPrimer_apellido());
//        } else {
//            nombre_miembro_header.setText(SIN_DATOS);
//        }
//
//        if (serverResponse.getGrupo_lidera().equals("true")) {
//            String s = "Lider del grupo ";
//            biografia.setText(s + serverResponse.getGrupo_nombre());
//        } else {
//            biografia.setText(SIN_DATOS);
//        }
//
//        if (serverResponse.getDireccion() != null && !serverResponse.getDireccion().equals("")) {
//            direccion.setText(serverResponse.getDireccion());
//        } else {
//            direccion.setText(SIN_DATOS);
//        }
//
//        if (serverResponse.getSegundo_apellido() != null) {
//            segundo_apellido.setText(serverResponse.getSegundo_apellido());
//        } else {
//            segundo_apellido.setText(SIN_DATOS);
//        }
//        if (serverResponse.getLideres() != null) {
//            lideres.setText(serverResponse.getLideres());
//        }
//        cedula.setText(serverResponse.getCedula());
//        nombre_miembro.setText(serverResponse.getMiembro());
//        primer_apellido.setText(serverResponse.getPrimer_apellido());
//
//        if (serverResponse.getTelefono() != null && !serverResponse.getTelefono().equals("")) {
//            telefono.setText(serverResponse.getTelefono());
//        } else {
//            telefono.setText(SIN_DATOS);
//        }
//
//        if (serverResponse.getEmail_miembro() != null) {
//            email.setText(serverResponse.getEmail_miembro());
//        }
//
//        String URL2 = "/static/Imagenes/cdr.com.png";
//        Picasso.with(getActivity()).load(URL_BASE + serverResponse.getFoto_perfil()).into(image);
//        Picasso.with(getActivity()).load(URL_BASE + URL2).into(background);
//
//        toggleProgressBar();
    }

    public void setServerError(ErrorEvent error) {
        this.serverError = error;
    }

    public ErrorEvent getServerError() {
        return serverError;
    }

    public void toggleProgressBar() {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
            progressBar.setAlpha(0.0f);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate().alpha(1.0f);
        } else {
            scrollView.setAlpha(0.0f);
            scrollView.setVisibility(View.VISIBLE);
            scrollView.animate().alpha(1.0f);
            progressBar.setVisibility(View.GONE);
        }
    }

    public static void toggleView(View v) {
        if (v.getVisibility() == View.VISIBLE) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
        v.setAlpha(0.0f);
        v.animate().alpha(1.0f);
    }

    @Override
    public void success(MiembroResponse serverResponse, Response response) {

        if (serverResponse.getResponse_code() == Constants.SUCCESS || serverResponse.getResponse_code() == Constants.CREATED) {

            if (serverResponse.getNombre() != null && !serverResponse.getNombre().equals("")) {
                String concatenate = serverResponse.getNombre() + " " + serverResponse.getPrimer_apellido();
                nombre_miembro_header.setText(concatenate);
            } else {
                nombre_miembro_header.setText(SIN_DATOS);
            }

            if (serverResponse.getGrupo_lidera().equals("true")) {
                String concatenate = "Lider del grupo " + serverResponse.getGrupo_nombre();
                biografia.setText(concatenate);
            } else {
                biografia.setText(SIN_DATOS);
            }

            if (serverResponse.getDireccion() != null && !serverResponse.getDireccion().equals("")) {
                direccion.setText(serverResponse.getDireccion());
            } else {
                direccion.setText(SIN_DATOS);
            }

            if (serverResponse.getSegundo_apellido() != null) {
                segundo_apellido.setText(serverResponse.getSegundo_apellido());
            } else {
                segundo_apellido.setText(SIN_DATOS);
            }
            if (serverResponse.getLideres() != null) {
                lideres.setText(serverResponse.getLideres());
            }
            cedula.setText(serverResponse.getCedula());
            nombre_miembro.setText(serverResponse.getNombre());
            primer_apellido.setText(serverResponse.getPrimer_apellido());

            if (serverResponse.getTelefono() != null && !serverResponse.getTelefono().equals("")) {
                telefono.setText(serverResponse.getTelefono());
            } else {
                telefono.setText(SIN_DATOS);
            }

            if (serverResponse.getEmail() != null) {
                email.setText(serverResponse.getEmail());
            }
            Picasso.with(getActivity()).load(Constants.URL_BASE + serverResponse.getFoto_perfil()).into(image);
        } else {
            Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
        }

        String URL2 = "/static/Imagenes/cdr.com.png";
        Picasso.with(getActivity()).load(Constants.URL_BASE + URL2).into(background);

        toggleProgressBar();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("E", "error desde retrofit en miembro");
        error.printStackTrace();
    }
}
