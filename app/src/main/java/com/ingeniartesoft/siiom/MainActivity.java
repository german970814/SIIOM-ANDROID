package com.ingeniartesoft.siiom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;

import com.ingeniartesoft.siiom.commucator.Communicator;
import com.ingeniartesoft.siiom.models.Grupo;
import com.ingeniartesoft.siiom.models.User;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.ingeniartesoft.siiom.tabs.SlidingTabLayout;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Private Variables
    private Communicator communicator;
//    private TextView nombre_miembro, shortbio, direccion, fecha_grupo, lideres, cedula, genero, nombre_grupo, direccion_grupo;
    private TextView nombre_miembro_header, nombre_miembro, primer_apellido,
            segundo_apellido, telefono, direccion, cedula,
            email, lideres, biografia;
    private EditText nombre_miembro_post,  primer_apellido_post, segundo_apellido_post, telefono_post, direccion_post, email_post, cedula_post;
    private FloatingActionButton button_success;
    private ProgressDialog progressDialog;

    // Constants
    static final String URL_BASE = "http://10.0.2.2:7000";
    static int ID_MIEMBRO;

    // Various
    ImageButton edit;

    // Pager
    ViewPageAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Perfil", "Grupo"};
    int Notabs = 2; // Titles.length; // 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content
        setContentView(R.layout.activity_main);
        // set toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // get sended data from intent
        Intent intent = getIntent();
        ID_MIEMBRO = intent.getIntExtra("MIEMBRO_ID", 1);

        // Dialogos de espera
        progressDialog = ProgressDialog.show(MainActivity.this, "", "Cargando. Espera Por Favor...", true);

        // Adapter for pages
        adapter = new ViewPageAdapter(getSupportFragmentManager(), Titles, Notabs, ID_MIEMBRO);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });
        tabs.setViewPager(pager);

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

    // for menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // useGet(1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_exit) {
            salir();
        }

        return super.onOptionsItemSelected(item);
    }

    // public void useGet(int id_grupo) {
        // communicator.getGrupo(id_grupo);
    // }


    // when has received a request or response from server
    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        // Toast.makeText(this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();

        if (serverEvent.getServerResponse().getResponseId() == 0) {
            ServerResponse serverResponse = serverEvent.getServerResponse();

            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (int i=0; i < fragments.size(); i++) {
                Fragment f = fragments.get(i);
                if (f instanceof Tab1) {
                    ((Tab1) f).setServerResponse(serverResponse, URL_BASE);
                } else if (f instanceof Tab2) {
                    ((Tab2) f).setServerResponse(serverResponse);
                } else {
                    Log.d("D", "No se envian los datos a los fragmentos");
                }
            }
        }
        // Para obetener las vistas se debe hacer dentro de este metodo
        ImageView background;
        background = (ImageView) findViewById(R.id.header_cover_image);
        // String URL1 = "http://10.0.2.2:7000/media/media/profile_pictures/user_125/55-888.png";
        String URL2 = "/static/Imagenes/cdr.com.png";

        Picasso.with(this).load(URL_BASE + URL2).into(background);

        edit = (ImageButton) findViewById(R.id.editar_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre_miembro_header = (TextView) findViewById(R.id.user_profile_name);
                biografia = (TextView) findViewById(R.id.user_profile_short_bio);
                lideres = (TextView) findViewById(R.id.lideres);

                direccion = (TextView) findViewById(R.id.direccion);
                cedula = (TextView) findViewById(R.id.cedula);
                nombre_miembro = (TextView) findViewById(R.id.nombre_miembro);
                primer_apellido = (TextView) findViewById(R.id.primer_apellido);
                segundo_apellido = (TextView) findViewById(R.id.segundo_apellido);
                telefono = (TextView) findViewById(R.id.telefono);
                email = (TextView) findViewById(R.id.email_m);

                nombre_miembro_post = (EditText) findViewById(R.id.nombre_miembro_post);
                cedula_post = (EditText) findViewById(R.id.cedula_post);
                telefono_post = (EditText) findViewById(R.id.telefono_post);
                email_post = (EditText) findViewById(R.id.email_post);
                primer_apellido_post = (EditText) findViewById(R.id.primer_apellido_post);
                segundo_apellido_post = (EditText) findViewById(R.id.segundo_apellido_post);
                direccion_post = (EditText) findViewById(R.id.direccion_post);
                button_success = (FloatingActionButton) findViewById(R.id.button_send_miembro);

                // toggleView(edit);
                toggleView(nombre_miembro);
                toggleView(nombre_miembro_post);
                nombre_miembro_post.setHint(nombre_miembro.getText());
                toggleView(primer_apellido);
                toggleView(primer_apellido_post);
                primer_apellido_post.setHint(primer_apellido.getText());
                toggleView(segundo_apellido);
                toggleView(segundo_apellido_post);
                segundo_apellido_post.setHint(segundo_apellido.getText());
                toggleView(cedula);
                toggleView(cedula_post);
                cedula_post.setHint(cedula.getText());
                toggleView(telefono);
                toggleView(telefono_post);
                telefono_post.setHint(telefono.getText());
                toggleView(email);
                toggleView(email_post);
                email_post.setHint(email.getText());
                toggleView(direccion);
                toggleView(direccion_post);
                direccion_post.setHint(direccion.getText());

                toggleView(button_success);

                if (button_success.getVisibility() == View.VISIBLE) {
                    edit.setBackgroundResource(R.mipmap.ic_close_white_24dp); // setBackground(R.mipmap.ic_close_white_24dp);
                } else {
                    edit.setBackgroundResource(R.mipmap.ic_create_white_24dp);
                }
            }
        });
        progressDialog.dismiss();
        // information.setText("" + serverEvent.getServerResponse().getMessage());
    }

    // when received response is an error
    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

    // static methods
    static public String get_week_day (int day) {
        String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

        try {
            return dias[day];
        }
        catch (Exception e) {
            return "INDEFINIDO";
        }
    }

    public void salir() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public static void toggleView (View v) {
        if (v.getVisibility() == View.VISIBLE) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
        v.setAlpha(0.0f);
        v.animate().alpha(1.0f);
    }
}
