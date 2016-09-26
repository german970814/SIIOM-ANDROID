package com.ingeniartesoft.siiom;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.io.ApiAdapter;
import com.ingeniartesoft.siiom.io.models.GrupoResponse;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.ingeniartesoft.siiom.ui.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by german on 28/08/16.
 */
public class GrupoFragment extends Fragment implements AdapterView.OnItemSelectedListener, Callback<GrupoResponse> {
    private int ID_MIEMBRO;

    // botones
    private FloatingActionButton button, button2, button3;

    // spinner
    private Spinner diaGrupo;

    // forms
    private TextInputLayout fHoraGrupo, fDireccionGrupo;
    private EditText fDiaGrupo;

    // textsviews dinamicos
    private TextView nLideres, nDiasGrupo, nDireccionGrupo, nHora, nEstado, nNombreGrupo, nDireccionGrupoBarra;

    // textsviews estaticos
    private TextView lDireccionGrupo, lHoraGrupo;

    // se crea un formulario con los edittexts
    private List<EditText> form = new ArrayList<EditText>();

//    private EditText hora_grupo_post, direccion_grupo_post, dia;
//    private TextView diaGrupoText, nombre_grupo, direccion_barra, lideres_grupo, estado, direccion, hora;

    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);

        ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", 1);

        // se sacan las vistas de texto
        nLideres = (TextView) v.findViewById(R.id.lideres_grupo);
        nDiasGrupo = (TextView) v.findViewById(R.id.diagrupo);
        nDireccionGrupo = (TextView) v.findViewById(R.id.direccion_grupo);
        nEstado = (TextView) v.findViewById(R.id.estado);
        nHora = (TextView) v.findViewById(R.id.hora_grupo);
        nNombreGrupo = (TextView) v.findViewById(R.id.nombre_grupo);
        nDireccionGrupoBarra = (TextView) v.findViewById(R.id.direccion_grupo_barra);

        // se sacan las vistas de formulario
        fHoraGrupo = (TextInputLayout) v.findViewById(R.id.hora_grupo_layout);
        fDireccionGrupo = (TextInputLayout) v.findViewById(R.id.direccion_grupo_layout);
        fDiaGrupo = (EditText) v.findViewById(R.id.dia);

        // se crea el spinner
        diaGrupo = (Spinner) v.findViewById(R.id.diagrupo_post);

        // se agregan a un formulario
        form.add(fDiaGrupo);
        form.add(fDireccionGrupo.getEditText());
        form.add(fHoraGrupo.getEditText());

        // se crean la progressbar y el scrollview
        progressBar = (ProgressBar) v.findViewById(R.id.progress_tab2);
        progressBar.setIndeterminate(true);
        scrollView = (ScrollView) v.findViewById(R.id.scrollview);

        // se crean los botones
        button = (FloatingActionButton) v.findViewById(R.id.button_send);
        button2 = (FloatingActionButton) v.findViewById(R.id.button_send2);
        button3 = (FloatingActionButton) v.findViewById(R.id.button_send3);

        // se crean las TextsViews Estaticas
        lHoraGrupo = (TextView) v.findViewById(R.id.label_hora_grupo);
        lDireccionGrupo = (TextView) v.findViewById(R.id.label_direccion_grupo);


//        nombre_grupo = (TextView) v.findViewById(R.id.nombre_grupo);
//        direccion_barra = (TextView) v.findViewById(R.id.direccion_grupo_barra);

//
//        diaGrupo = (Spinner) v.findViewById(R.id.diagrupo_post);
//        diaGrupoText = (TextView) v.findViewById(R.id.diagrupo);
//        lideres_grupo = (TextView) v.findViewById(R.id.lideres_grupo);
//        estado = (TextView) v.findViewById(R.id.estado);
//        hora = (TextView) v.findViewById(R.id.hora_grupo);
//        hora_grupo_post = (EditText) v.findViewById(R.id.hora_grupo_post);
//        direccion = (TextView) v.findViewById(R.id.direccion_grupo);
//        direccion_grupo_post = (EditText) v.findViewById(R.id.direccion_grupo_post);
//        dia = (EditText) v.findViewById(R.id.dia);

        // se creaa una lista de dias como opciones para el spinner
        List<String> dias = new ArrayList<String>();
        dias.add("Lunes");
        dias.add("Martes");
        dias.add("Miercoles");
        dias.add("Jueves");
        dias.add("Viernes");
        dias.add("Sabado");
        dias.add("Domingo");

        // se crea adaptador para el dropdown de dias
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diaGrupo.setAdapter(dataAdapter);
//
//        scrollView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (button2.getVisibility() == View.VISIBLE) {
//                    scrollView.setAlpha(0.2f);
//                    scrollView.animate().alpha(1.0f);
//                    button3.animate().translationY(0.0f);
//                    button3.setVisibility(View.GONE);
//                    button.setBackgroundResource(R.mipmap.ic_create_white_24dp);
//                }
//            }
//        });
//
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                scrollView.setAlpha(0.2f);
                scrollView.animate().alpha(1.0f);
                button3.animate().translationY(0.0f);
                button3.setVisibility(View.GONE);
                button2.animate().translationY(0.0f);
                button2.setVisibility(View.GONE);
                button.setImageResource(R.mipmap.ic_create_white_24dp);

                toggleView(nDireccionGrupo);
                toggleView(fDireccionGrupo);
                toggleView(lDireccionGrupo);
                toggleView(diaGrupo);
                toggleView(nDiasGrupo);
                toggleView(nHora);
                toggleView(lHoraGrupo);
                toggleView(fHoraGrupo);
            }
        });
//
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                fDiaGrupo.setText(String.valueOf(get_number_day(diaGrupo.getSelectedItem().toString())));

                int Errors = 0;

                for (EditText field: form) {
                    if (!prevent_set_empty_data(field)) {
                        Errors++;
                    }
                }

                if (Errors == 0) {
                    ApiAdapter.getApiService().editar_grupo(
                            ID_MIEMBRO, fDiaGrupo.getText().toString(), fHoraGrupo.getEditText().getText().toString(),
                            fDireccionGrupo.getEditText().getText().toString(), GrupoFragment.this
                    );
                    button2.performClick();
                    button.setImageResource(R.mipmap.ic_create_white_24dp);
                    toggleLoading();
                }
            }
        });
//
//        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    scrollView.setAlpha(0.2f);
//                    scrollView.animate().alpha(1.0f);
//                    button2.animate().translationY(0.0f);
//                    button2.setVisibility(View.GONE);
//                    button3.animate().translationY(0.0f);
//                    button3.setVisibility(View.GONE);
//                    button.setImageResource(R.mipmap.ic_adjust_white_24dp);
//                }
//            }
//        });
//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if (button2.getVisibility() == View.GONE) {
                    if (fDireccionGrupo.getVisibility() == View.GONE) {
                        button.setImageResource(R.mipmap.ic_apps_white_24dp);

                        toggleView(nDireccionGrupo);
                        if (fDireccionGrupo.getEditText() != null) {
                            fDireccionGrupo.getEditText().setText(nDireccionGrupo.getText().toString());
                        }
                        toggleView(fDireccionGrupo);

                        toggleView(diaGrupo);
                        toggleView(nDiasGrupo);
                        toggleView(lDireccionGrupo);
                        diaGrupo.setSelection(get_number_day(nDiasGrupo.getText().toString()));

                        toggleView(nHora);
                        if (fHoraGrupo.getEditText() != null) {
                            fHoraGrupo.getEditText().setText(nHora.getText().toString());
                        }
                        toggleView(fHoraGrupo);
                        toggleView(lHoraGrupo);
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

    @Override
    public void onResume() {
        super.onResume();
        ApiAdapter.getApiService().get_grupo(ID_MIEMBRO, this);
        toggleLoading();
    }

    @Override
    public void success(GrupoResponse serverResponse, Response response) {
        if (serverResponse.getResponse_code() == Constants.SUCCESS || serverResponse.getResponse_code() == Constants.CREATED) {
            if (validate_data_string(serverResponse.getNombre())) {
                nNombreGrupo.setText(serverResponse.getNombre());
            } else {
                nNombreGrupo.setText(Constants.SIN_DATOS);
            }

            if (validate_data_string(serverResponse.getDireccion())) {
                nDireccionGrupoBarra.setText(serverResponse.getDireccion());
                nDireccionGrupo.setText(serverResponse.getDireccion());
            } else {
                nDireccionGrupoBarra.setText(Constants.SIN_DATOS);
                nDireccionGrupo.setText(Constants.SIN_DATOS);
            }

            String concatena;
            if (validate_data_string(serverResponse.getLider1())) {
                concatena = serverResponse.getLider1();
            } else {
                concatena = Constants.SIN_DATOS;
            }

            if (!concatena.equals(Constants.SIN_DATOS)) {
                if (validate_data_string(serverResponse.getLider2())) {
                    concatena = concatena + " y " + serverResponse.getLider2();
                }
            }

            nLideres.setText(concatena);

            if (validate_data_string(serverResponse.getDia_grupo())) {
                nDiasGrupo.setText(get_week_day(Integer.parseInt(serverResponse.getDia_grupo())));
            } else {
                nDiasGrupo.setText(Constants.SIN_DATOS);
            }

            if (validate_data_string(serverResponse.getHora_grupo())) {
                nHora.setText(serverResponse.getHora_grupo());
            } else {
                nHora.setText(Constants.SIN_DATOS);
            }

            if (validate_data_string(serverResponse.getEstado())) {
                nEstado.setText(serverResponse.getEstado());
            } else {
                nEstado.setText(Constants.SIN_DATOS);
            }
        } else if (serverResponse.getResponse_code() == Constants.DENIED || serverResponse.getResponse_code() == Constants.ERROR || serverResponse.getResponse_code() == Constants.NOT_FOUND){
            Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
        toggleLoading();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("F", "Error desde el grupoFragment");
        error.printStackTrace();
    }

    public void toggleLoading() {
        if (progressBar != null && scrollView != null) {
            toggleView(progressBar);
            toggleView(scrollView);
        }
    }

    public boolean validate_data_string(String data) {
        return data != null && !data.isEmpty();
    }

    public boolean prevent_set_empty_data(EditText editText) {
        if (editText != null) {
            if (editText.getText().toString().isEmpty()) {
                if (editText.getParent() instanceof TextInputLayout) {
                    ((TextInputLayout) editText.getParent()).setError("Este Campo no puede estar vacio");
                }
                return false;
            }
            return true;
        }
        return false;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
