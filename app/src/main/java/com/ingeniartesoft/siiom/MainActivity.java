package com.ingeniartesoft.siiom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ingeniartesoft.siiom.commucator.Communicator;
import com.ingeniartesoft.siiom.server.ErrorEvent;
import com.ingeniartesoft.siiom.server.ServerEvent;
import com.ingeniartesoft.siiom.server.ServerResponse;
import com.ingeniartesoft.siiom.tabs.SlidingTabLayout;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.otto.Subscribe;

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content
        setContentView(R.layout.activity_main);

        // get sended data from intent
        Intent intent = getIntent();
        ID_MIEMBRO = intent.getIntExtra("MIEMBRO_ID", 1);

        // Dialogos de espera
        //progressDialog = ProgressDialog.show(MainActivity.this, "", "Cargando. Espera Por Favor...", true);

        // Adapter for pages
//        adapter = new ViewPageAdapter(getSupportFragmentManager(), Titles, Notabs, ID_MIEMBRO);
//        pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(adapter);
//
//        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
//        tabs.setDistributeEvenly(true);
//        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.buttonColor);
//            }
//        });
//        tabs.setViewPager(pager);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.tab_miembro);

        final Bundle bundle = new Bundle();
        bundle.putInt("ID_MIEMBRO", ID_MIEMBRO);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_miembro:
                        MiembroFragment miembroFragment = new MiembroFragment();
                        miembroFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, miembroFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                        break;
                    case R.id.tab_grupo:
                        GrupoFragment grupoFragment = new GrupoFragment();
                        grupoFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, grupoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_discipulos:
                        DiscipulosFragment discipulosFragment = new DiscipulosFragment();
                        discipulosFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, discipulosFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        if (id == R.id.action_exit) {
//            salir();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
