package com.ingeniartesoft.siiom;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ServerError;
import com.ingeniartesoft.siiom.commucator.Communicator;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by german on 28/08/16.
 */
public class Tab2 extends Fragment implements AdapterView.OnItemSelectedListener {
    Communicator communicator;
    private ErrorEvent serverError;

    FloatingActionButton button, button2, button3;
    Spinner diaGrupo;
    EditText hora_grupo_post, direccion_grupo_post, dia;
    TextView diaGrupoText, nombre_grupo, direccion_barra, lideres_grupo, estado, direccion, hora;

    ProgressBar progressBar;
    ScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);

        final int ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", 1);

        communicator = new Communicator();

        nombre_grupo = (TextView) v.findViewById(R.id.nombre_grupo);
        direccion_barra = (TextView) v.findViewById(R.id.direccion_grupo_barra);
        button = (FloatingActionButton) v.findViewById(R.id.button_send);
        button2 = (FloatingActionButton) v.findViewById(R.id.button_send2);
        button3 = (FloatingActionButton) v.findViewById(R.id.button_send3);

        diaGrupo = (Spinner) v.findViewById(R.id.diagrupo_post);
        diaGrupoText = (TextView) v.findViewById(R.id.diagrupo);
        lideres_grupo = (TextView) v.findViewById(R.id.lideres_grupo);
        estado = (TextView) v.findViewById(R.id.estado);
        hora = (TextView) v.findViewById(R.id.hora_grupo);
        hora_grupo_post = (EditText) v.findViewById(R.id.hora_grupo_post);
        direccion = (TextView) v.findViewById(R.id.direccion_grupo);
        direccion_grupo_post = (EditText) v.findViewById(R.id.direccion_grupo_post);
        dia = (EditText) v.findViewById(R.id.dia);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_tab2);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        scrollView = (ScrollView) v.findViewById(R.id.scrollview);
        scrollView.setVisibility(View.GONE);

        List<String> dias = new ArrayList<String>();
        dias.add("Lunes");
        dias.add("Martes");
        dias.add("Miercoles");
        dias.add("Jueves");
        dias.add("Viernes");
        dias.add("Sabado");
        dias.add("Domingo");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diaGrupo.setAdapter(dataAdapter);

        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button2.getVisibility() == View.VISIBLE) {
                    scrollView.setAlpha(0.2f);
                    scrollView.animate().alpha(1.0f);
                    button3.animate().translationY(0.0f);
                    button3.setVisibility(View.GONE);
                    button.setBackgroundResource(R.mipmap.ic_create_white_24dp);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setAlpha(0.2f);
                scrollView.animate().alpha(1.0f);
                button3.animate().translationY(0.0f);
                button3.setVisibility(View.GONE);
                button2.animate().translationY(0.0f);
                button2.setVisibility(View.GONE);
                button.setImageResource(R.mipmap.ic_create_white_24dp);
                toggleView(direccion);
                toggleView(direccion_grupo_post);
                toggleView(diaGrupo);
                toggleView(diaGrupoText);
                toggleView(hora);
                toggleView(hora_grupo_post);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverError = null;
                dia.setText(String.valueOf(get_number_day(diaGrupo.getSelectedItem().toString())));
                editarGrupoPost(ID_MIEMBRO,
                        direccion_grupo_post.getText().toString(),
                        dia.getText().toString(),
                        hora.getText().toString());

                if (getServerError() == null) {
                    communicator.getGrupo(ID_MIEMBRO);
                    button2.performClick();
                }
                button.setImageResource(R.mipmap.ic_create_white_24dp);
            }
        });

        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    scrollView.setAlpha(0.2f);
                    scrollView.animate().alpha(1.0f);
                    button2.animate().translationY(0.0f);
                    button2.setVisibility(View.GONE);
                    button3.animate().translationY(0.0f);
                    button3.setVisibility(View.GONE);
                    button.setImageResource(R.mipmap.ic_adjust_white_24dp);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button2.getVisibility() == View.GONE) {
                    if (direccion_grupo_post.getVisibility() == View.GONE) {
//                        button.setBackgroundResource(R.mipmap.ic_adjust_white_24dp);
                        button.setImageResource(R.mipmap.ic_adjust_white_24dp);

                        toggleView(direccion);
                        direccion_grupo_post.setText(direccion.getText());
                        toggleView(direccion_grupo_post);
                        toggleView(diaGrupo);
                        toggleView(diaGrupoText);
                        diaGrupo.setSelection(get_number_day(diaGrupoText.getText().toString()));
                        toggleView(hora);
                        hora_grupo_post.setText(hora.getText());
                        toggleView(hora_grupo_post);
                    } else {
                        scrollView.setAlpha(1.0f);
                        scrollView.animate().alpha(0.2f);
                        button3.setVisibility(View.VISIBLE);
                        button3.animate().translationY(-button2.getHeight() - (button2.getHeight() / 3));
                        button2.setVisibility(View.VISIBLE);
                        button2.animate().translationY((-button2.getHeight() - (button2.getHeight() / 3)) * 2);
                    }

                } else {
                    scrollView.setAlpha(0.2f);
                    scrollView.animate().alpha(1.0f);
                    button2.animate().translationY(0.0f);
                    button2.setVisibility(View.GONE);
                    button3.animate().translationY(0.0f);
                    button3.setVisibility(View.GONE);
//                    button.setBackgroundResource(R.mipmap.ic_create_white_24dp);
//                    button.setImageResource(R.mipmap.ic_create_white_24dp);
                }
            }
        });

        return v;
    }

    public void setServerResponse (ServerResponse serverResponse) {
        nombre_grupo.setText(serverResponse.getGrupo_nombre());
        direccion_barra.setText(serverResponse.getDireccion_grupo());
        lideres_grupo.setText(serverResponse.getGrupo_lider1() + " - " + serverResponse.getGrupo_lider2());
        direccion.setText(serverResponse.getDireccion_grupo());
        diaGrupoText.setText(get_week_day(Integer.parseInt(serverResponse.getDia_grupo())));
        hora.setText(serverResponse.getHora_grupo());
        estado.setText(serverResponse.getEstado_grupo());
    }

    public void setServerError(ErrorEvent serverError) {
        this.serverError = serverError;
    }

    public ErrorEvent getServerError() {
        return serverError;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (progressBar != null && scrollView != null) {
            if (isVisibleToUser) {
                progressBar.setVisibility(View.GONE);
                scrollView.setAlpha(0.0f);
                scrollView.animate().alpha(1.0f);
                scrollView.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setAlpha(0.0f);
                scrollView.animate().alpha(1.0f);
                scrollView.setVisibility(View.GONE);
            }
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

    static public String get_week_day (int day) {
        String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

        try {
            return dias[day];
        }
        catch (Exception e) {
            return "INDEFINIDO";
        }
    }

    static public int get_number_day (String day) {
        String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        for (int i = 0; i < dias.length; i++) {
            if (dias[i].equals(day)) {
                return i;
            }
        }
        return -1;
    }

    private void editarGrupoPost(int id, String direccion, String dia, String hora) {
        communicator.editarGrupoPost(id, direccion, dia, hora);
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        int n = get_number_day(item);
//        Toast.makeText(getActivity(), "dia seteado a " + String.valueOf(n), Toast.LENGTH_SHORT).show();
//        if (n >= 0 && n <= 6) {
//            dia.setText(String.valueOf(n));
//            Log.d("F", "dia seteado a " + String.valueOf(n));
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Nothing
    }
}
