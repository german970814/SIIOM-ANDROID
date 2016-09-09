package com.ingeniartesoft.siiom;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.server.ServerResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by german on 28/08/16.
 */
public class Tab2 extends Fragment implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    FloatingActionButton button;
    EditText diaGrupo;
    TextView diaGrupoText;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);

        //final String[]paths = {"Editar", "Borrar"};

        //Spinner spinner;
        //spinner = (Spinner) v.findViewById(R.id.more);

        //ArrayAdapter<CharSequence>adapter = new ArrayAdapter.createFromResource(getActivity(), R.array.brew_array, R.layout.simple_spinner_item);

        spinner = (Spinner) v.findViewById(R.id.more);

        List<String> list = new ArrayList<String>();
        list.add("Editar");
        list.add("Borrar");

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);
        spinner.setOnItemSelectedListener(this);
        button = (FloatingActionButton) v.findViewById(R.id.button_send);
        diaGrupo = (EditText) v.findViewById(R.id.diagrupo_post);
        diaGrupoText = (TextView) v.findViewById(R.id.diagrupo);

        return v;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.setSelection(1); //comment this line
        String selState = (String) spinner.getSelectedItem();
        Log.d("D", "Se hhizo click en algun texto");
        Toast.makeText(getActivity(), "" + "Se he seleccionado" + selState, Toast.LENGTH_SHORT).show();
        if (selState.equals("Editar")) {
            //FloatingActionButton button;
            //button = (FloatingActionButton) view.findViewById(R.id.button_send);
            button.setVisibility(View.VISIBLE);
            diaGrupo.setVisibility(View.VISIBLE);
            diaGrupoText.setVisibility(View.GONE);
        } else {
            //FloatingActionButton button;
            //button = (FloatingActionButton) view.findViewById(R.id.button_send);
            button.setVisibility(View.GONE);
            diaGrupo.setVisibility(View.GONE);
            diaGrupoText.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void setServerResponse (ServerResponse serverResponse) {
        Log.d("D", "Entre el server sersponse de tab2");
    }
}
