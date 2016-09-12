package com.ingeniartesoft.siiom;

import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Private Variables
    private Communicator communicator;
    private ProgressDialog progressDialog;

    // Constants
    static final String URL_BASE = "http://10.0.2.2:7000";
    static int ID_MIEMBRO;

    // Pager
    ViewPageAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Perfil", "Grupo", "Discipulos"};
    int Notabs = Titles.length; // 2;

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
        //progressDialog = ProgressDialog.show(MainActivity.this, "", "Cargando. Espera Por Favor...", true);

        // Adapter for pages
        adapter = new ViewPageAdapter(getSupportFragmentManager(), Titles, Notabs, ID_MIEMBRO);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.buttonColor);
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


    // when has received a request or response from server
    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        // Toast.makeText(this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        setupUI(findViewById(R.id.parent_main));
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
        // progressDialog.dismiss();
        // information.setText("" + serverEvent.getServerResponse().getMessage());
    }

    // when received response is an error
    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();

        if (errorEvent.getErrorCode() != 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (int i=0; i < fragments.size(); i++) {
                Fragment f = fragments.get(i);
                if (f instanceof Tab1) {
                    ((Tab1) f).setServerError(errorEvent);
                } else if (f instanceof Tab2) {
                    ((Tab2) f).setServerError(errorEvent);
                } else {
                    Log.d("D", "No se envian los datos a los fragmentos");
                }
            }
        }
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

    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setupUI (View view) {
        if ((view instanceof EditText)) {
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        hideSoftKeyboard(view);
                    }
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
