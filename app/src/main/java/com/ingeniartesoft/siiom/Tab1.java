package com.ingeniartesoft.siiom;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.commucator.Communicator;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by german on 28/08/16.
 */
public class Tab1 extends Fragment {
    Communicator communicator;
    public final String TAG = "TAB1";

    private TextView nombre_miembro_header, nombre_miembro, primer_apellido,
            segundo_apellido, telefono, direccion, cedula,
            email, lideres, biografia;
    private EditText nombre_miembro_post,  primer_apellido_post, segundo_apellido_post, telefono_post, direccion_post, email_post, cedula_post;
    private FloatingActionButton button_success;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);

        communicator = new Communicator();
        // nombre_grupo = (TextView) v.findViewById(R.id.nombreGrupo);


        Log.d("SE", "SETEADO");
        int ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", 1);
        useGet(ID_MIEMBRO);


        // se inicializan las variables
        nombre_miembro_header = (TextView) v.findViewById(R.id.user_profile_name);
        biografia = (TextView) v.findViewById(R.id.user_profile_short_bio);
        lideres = (TextView) v.findViewById(R.id.lideres);

        direccion = (TextView) v.findViewById(R.id.direccion);
        cedula = (TextView) v.findViewById(R.id.cedula);
        nombre_miembro = (TextView) v.findViewById(R.id.nombre_miembro);
        primer_apellido = (TextView) v.findViewById(R.id.primer_apellido);
        segundo_apellido = (TextView) v.findViewById(R.id.segundo_apellido);
        telefono = (TextView) v.findViewById(R.id.telefono);
        email = (TextView) v.findViewById(R.id.email);

        nombre_miembro_post = (EditText) v.findViewById(R.id.nombre_miembro_post);
        cedula_post = (EditText) v.findViewById(R.id.cedula_post);
        telefono_post = (EditText) v.findViewById(R.id.telefono_post);
        email_post = (EditText) v.findViewById(R.id.email_post);
        primer_apellido_post = (EditText) v.findViewById(R.id.primer_apellido_post);
        segundo_apellido_post = (EditText) v.findViewById(R.id.segundo_apellido_post);
        direccion_post = (EditText) v.findViewById(R.id.direccion_post);

        button_success = (FloatingActionButton) v.findViewById(R.id.button_send_miembro);
        image = (ImageView) v.findViewById(R.id.user_profile_photo);

        return v;
    }

    public void useGet(int id_grupo) {
        communicator.getGrupo(id_grupo);
    }

    public void setServerResponse (ServerResponse serverResponse, String URL_BASE) {
        if (nombre_miembro_header != null) {
            nombre_miembro_header.setText("" + serverResponse.getMiembro());
        } else {
            Log.d("D", "HEADER NULL");
        }

        if (biografia != null) {
            if (serverResponse.getGrupo_lidera().equals("true")) {
                biografia.setText("Lider de el grupo " + serverResponse.getGrupo_nombre());
            } else {
                biografia.setText("No tiene Grupo Asignado");
            }
        } else {
            Log.d("D", "BIOGRAFIA NULL");
        }

        if (serverResponse.getDireccion() != null) {
            direccion.setText(serverResponse.getDireccion());
        } else {
            Log.d("D", "Aqui debe explotar");
            direccion.setText("SIN DATOS");
            Log.d("D", "Explota aqui");
        }
        Log.d("D", "No exploto aqui");
        if (serverResponse.getSegundo_apellido() != null) {
            segundo_apellido.setText(serverResponse.getSegundo_apellido());
        } else {
            segundo_apellido.setText("SIN DATOS");
        }
        if (serverResponse.getLideres() != null) {
            lideres.setText(serverResponse.getLideres());
        }
        cedula.setText(serverResponse.getCedula());
        nombre_miembro.setText(serverResponse.getMiembro());
        primer_apellido.setText(serverResponse.getPrimer_apellido());

        if (serverResponse.getTelefono() != null) {
            telefono.setText(serverResponse.getTelefono());
        }
        if (serverResponse.getEmail_miembro() != null) {
            email.setText(serverResponse.getEmail_miembro());
        }

//            fecha_grupo.setText(get_week_day(Integer.parseInt(serverResponse.getFecha_grupo())));
//            genero.setText(serverResponse.getGenero());
//            nombre_grupo.setText(serverResponse.getGrupoName());
//            direccion_grupo.setText(serverResponse.getDireccion_grupo());


        Picasso.with(getActivity()).load(URL_BASE + serverResponse.getFoto_perfil()).into(image);
    }
}
