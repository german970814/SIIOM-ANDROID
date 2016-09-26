package com.ingeniartesoft.siiom;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ingeniartesoft.siiom.io.ApiAdapter;
import com.ingeniartesoft.siiom.io.models.MiembroResponse;
import com.ingeniartesoft.siiom.ui.Constants;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

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

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView background;

    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    private Toolbar toolbar;

    private enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private FloatingActionButton button_success1, button_success2, button_success3, button_success4;

    // TextxViews Dinamicos
    private TextView nNombre, nPrimerApellido, nSegundoApellido, nCedula, nTelefono, nEmail, nDireccion;

    // TextxViews Estaticos
    private TextView lNombre, lPrimerApellido, lSegundoApellido, lCedula, lTelefono, lEmail, lDireccion;

    // Forms
    private TextInputLayout fNombre, fPrimerApellido, fSegundoApellido, fCedula, fTelefono, fEmail, fDireccion;

    private FrameLayout frameWrapper;

    private List<TextInputLayout> form = new ArrayList<TextInputLayout>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);
        ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", 1);

        // se inicializan las vistas

        collapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        background = (ImageView) v.findViewById(R.id.user_profile_photo);
        toolbar = (Toolbar) v.findViewById(R.id.actiontoolbal);
        AppBarLayout appBarLayout = (AppBarLayout) v.findViewById(R.id.app_bar);

        button_success1 = (FloatingActionButton) v.findViewById(R.id.button_edit_1);
        button_success2 = (FloatingActionButton) v.findViewById(R.id.button_edit_2);
        button_success3 = (FloatingActionButton) v.findViewById(R.id.button_edit_3);
        button_success4 = (FloatingActionButton) v.findViewById(R.id.button_edit_4);

        nestedScrollView = (NestedScrollView) v.findViewById(R.id.inc); //.findViewById(R.id.nscroll);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_tab1);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private State state;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != State.EXPANDED) {
                        button_success2.animate().alpha(0.0f);
                        button_success2.setVisibility(View.GONE);
                    }
                    state = State.EXPANDED;
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != State.COLLAPSED) {
                        button_success2.setVisibility(View.VISIBLE);
                        button_success2.setAlpha(0.0f);
                        button_success2.animate().alpha(1.0f).setDuration(1000);
                    }
                    state = State.COLLAPSED;
                } else {
                    if (state != State.IDLE) {
                        Log.d("D", "IDLE************");
                    }
                    state = State.IDLE;
                }

            }
        });

        // TextsViews Dinamicos
        nNombre = (TextView) v.findViewById(R.id.nombre_miembro);
        nPrimerApellido = (TextView) v.findViewById(R.id.primer_apellido);
        nSegundoApellido = (TextView) v.findViewById(R.id.segundo_apellido);
        nCedula = (TextView) v.findViewById(R.id.cedula);
        nTelefono = (TextView) v.findViewById(R.id.telefono);
        nDireccion = (TextView) v.findViewById(R.id.direccion);
        nEmail = (TextView) v.findViewById(R.id.email_m);

        // TextsViews Estaticos
        lNombre = (TextView) v.findViewById(R.id.label_nombre);
        lPrimerApellido = (TextView) v.findViewById(R.id.label_primer_apellido);
        lSegundoApellido = (TextView) v.findViewById(R.id.label_segundo_apellido);
        lCedula = (TextView) v.findViewById(R.id.label_cedula);
        lTelefono = (TextView) v.findViewById(R.id.label_telefono);
        lDireccion = (TextView) v.findViewById(R.id.label_direccion);
        lEmail = (TextView) v.findViewById(R.id.label_email);

        // Forms
        fNombre = (TextInputLayout) v.findViewById(R.id.nombre_layout);
        fPrimerApellido = (TextInputLayout) v.findViewById(R.id.primer_apellido_layout);
        fSegundoApellido = (TextInputLayout) v.findViewById(R.id.segundo_apellido_layout);
        fCedula = (TextInputLayout) v.findViewById(R.id.cedula_layout);
        fTelefono = (TextInputLayout) v.findViewById(R.id.telefono_layout);
        fDireccion = (TextInputLayout) v.findViewById(R.id.direccion_layout);
        fEmail = (TextInputLayout) v.findViewById(R.id.email_layout);

        frameWrapper = (FrameLayout) v.findViewById(R.id.wrapper_button);

        addOnclickEvent(button_success2);
        addOnclickEvent(button_success1);


        // se agregan las variables a el formulario
        form.add(fNombre);
        form.add(fPrimerApellido);
        form.add(fSegundoApellido);
        form.add(fCedula);
        form.add(fEmail);
        form.add(fTelefono);
        form.add(fDireccion);

        for (TextInputLayout field: form) {
            addOnChangeFocusEvent(field.getEditText());
        }

        toggleProgressBar();

        frameWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                toggleView(button_success4);
                toggleView(button_success3);
                toggleView(frameWrapper);
                if (button_success1.getVisibility() == View.VISIBLE) {
                    toggleView(button_success2);
                }
            }
        });

        button_success4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
//                button_success4.requestFocus();
                toggleView(frameWrapper);
                toggleView(button_success3);
                toggleView(button_success4);
                button_success2.setImageResource(R.mipmap.ic_create_white_24dp);
                button_success1.setImageResource(R.mipmap.ic_create_white_24dp);

                toggleView(lNombre);
                toggleView(nNombre);
                toggleView(fNombre);

                toggleView(lPrimerApellido);
                toggleView(nPrimerApellido);
                toggleView(fPrimerApellido);

                toggleView(lSegundoApellido);
                toggleView(nSegundoApellido);
                toggleView(fSegundoApellido);

                toggleView(lCedula);
                toggleView(nCedula);
                toggleView(fCedula);

                toggleView(lTelefono);
                toggleView(nTelefono);
                toggleView(fTelefono);

                toggleView(lDireccion);
                toggleView(nDireccion);
                toggleView(fDireccion);

                toggleView(lEmail);
                toggleView(nEmail);
                toggleView(fEmail);
            }
        });

        button_success3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                button_success3.requestFocus();
                hideKeyboard(view);
                int Errors = 0;

                for (TextInputLayout field : form) {
                    if (!prevent_send_empty_data(field)) {
                        Errors++;
                    }
                }

                if (Errors == 0) {
                    ApiAdapter.getApiService().editar_miembro(
                            ID_MIEMBRO,
                            fNombre.getEditText().getText().toString(),
                            fPrimerApellido.getEditText().getText().toString(),
                            fSegundoApellido.getEditText().getText().toString(),
                            fCedula.getEditText().getText().toString(),
                            fEmail.getEditText().getText().toString(),
                            fTelefono.getEditText().getText().toString(),
                            fDireccion.getEditText().getText().toString(),
                            MiembroFragment.this);
                    toggleProgressBar();
//                edit.performClick();
                }
            }
        });

        showToolbar(v, "SIIOM", false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ApiAdapter.getApiService().get_miembro(ID_MIEMBRO, this);
    }

    public void showToolbar(@NonNull View view, String title, boolean upButton) {
        // Toolbar toolbar = (Toolbar) view.findViewById(R.id.actiontoolbal);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (title != null && !title.isEmpty()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


    public void toggleProgressBar() {
        if (nestedScrollView.getVisibility() == View.VISIBLE) {
            nestedScrollView.setVisibility(View.GONE);
            progressBar.setAlpha(0.0f);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate().alpha(1.0f);
        } else {
            nestedScrollView.setAlpha(0.0f);
            nestedScrollView.setVisibility(View.VISIBLE);
            nestedScrollView.animate().alpha(1.0f);
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
                    .with(getActivity())
                    .load(Constants.URL_BASE + serverResponse.getFoto_perfil())
                    .resize(1600,800)
                    .into(background);

            if (button_success4.getVisibility() == View.VISIBLE) {
                button_success4.performClick();
            }

        } else {
            if (serverResponse.getResponse_code() == Constants.ERROR || serverResponse.getResponse_code() == Constants.DENIED) {
                if (fNombre.getEditText() != null) {
                    fNombre.getEditText().setError("Ha ocurrido un error");
                }
            }
            Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
        }

        // String URL2 = "/static/Imagenes/cdr.com.png";
        // Picasso.with(getActivity()).load(Constants.URL_BASE + URL2).into(background);

        toggleProgressBar();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("E", "error desde retrofit en miembro");
        error.printStackTrace();
    }

    public boolean validate_string (String data) {
        return data != null && !data.isEmpty();
    }

    public void addOnclickEvent (final FloatingActionButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if (fNombre.getVisibility() == View.GONE) {
                    toggleView(lNombre);
                    toggleView(nNombre);
                    toggleView(fNombre);
                    if (fNombre.getEditText() != null) {
                        fNombre.getEditText().setText(nNombre.getText().toString());
                    }

                    toggleView(lPrimerApellido);
                    toggleView(nPrimerApellido);
                    toggleView(fPrimerApellido);
                    if (fPrimerApellido.getEditText() != null) {
                        fPrimerApellido.getEditText().setText(nPrimerApellido.getText().toString());
                    }

                    toggleView(lSegundoApellido);
                    toggleView(nSegundoApellido);
                    toggleView(fSegundoApellido);
                    if (fSegundoApellido.getEditText() != null) {
                        fSegundoApellido.getEditText().setText(nSegundoApellido.getText().toString());
                    }

                    toggleView(lCedula);
                    toggleView(nCedula);
                    toggleView(fCedula);
                    if (fCedula.getEditText() != null) {
                        fCedula.getEditText().setText(nCedula.getText().toString());
                    }

                    toggleView(lTelefono);
                    toggleView(nTelefono);
                    toggleView(fTelefono);
                    if (fTelefono.getEditText() != null) {
                        fTelefono.getEditText().setText(nTelefono.getText().toString());
                    }

                    toggleView(lDireccion);
                    toggleView(nDireccion);
                    toggleView(fDireccion);
                    if (fDireccion.getEditText() != null) {
                        fDireccion.getEditText().setText(nDireccion.getText().toString());
                    }

                    toggleView(lEmail);
                    toggleView(nEmail);
                    toggleView(fEmail);
                    if (fEmail.getEditText() != null) {
                        fEmail.getEditText().setText(nEmail.getText().toString());
                    }

                    button_success2.setImageResource(R.mipmap.ic_apps_white_24dp);
                    button_success1.setImageResource(R.mipmap.ic_apps_white_24dp);
                } else {
                    if (button_success3.getVisibility() == View.GONE) {
                        toggleView(frameWrapper);
//                        button_success3.setVisibility(View.VISIBLE);
                        toggleView(button_success3);
                        toggleView(button_success4);
                        button_success3.animate().translationY(-button_success2.getHeight() - (button_success2.getHeight() / 3));
                        button_success4.animate().translationY((-button_success2.getHeight() - (button_success2.getHeight() / 3)) *2);

                        if (button_success2.getVisibility() == View.GONE) {
                            button_success2.setVisibility(View.VISIBLE);
                        }
                        button_success2.setAlpha(1.0f);
                    } else {
                        toggleView(frameWrapper);
                        toggleView(button_success3);
                        toggleView(button_success4);
                        if (button_success1.getVisibility() == View.VISIBLE) {
                            toggleView(button_success2);
                        }
                    }
                }
            }
        });
    }

    public boolean prevent_send_empty_data(TextInputLayout view) {
        if (view.getEditText() != null) {
            if (view.getEditText().getText().toString().isEmpty()) {
                view.setError("Este campo no puede estar vacio");
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void addOnChangeFocusEvent(EditText v) {
        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_exit) {
//            MainActivity.salir();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
