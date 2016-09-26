package com.ingeniartesoft.siiom;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingeniartesoft.siiom.adapters.DiscipulosAdapter;
import com.ingeniartesoft.siiom.io.ApiAdapter;
import com.ingeniartesoft.siiom.io.models.DiscipulosResponse;
import com.ingeniartesoft.siiom.ui.ItemOffsetDecoration;
import com.ingeniartesoft.siiom.models.Discipulo;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by german on 12/09/16.
 */
public class DiscipulosFragment extends Fragment implements Callback<DiscipulosResponse> {
    private RecyclerView mDiscipulosList;
    public static final int NUM_COLUMNAS = 2;
    private DiscipulosAdapter adapter;
    private int ID_MIEMBRO;
//    private SearchView searchView;

    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new DiscipulosAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3, container, false);

        mDiscipulosList = (RecyclerView) v.findViewById(R.id.discipulos_list);
//        searchView = (SearchView) v.findViewById(R.id.search_discipulos);

//        setHasOptionsMenu(true);
        ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", ID_MIEMBRO);
        toolbar = (Toolbar) v.findViewById(R.id.actionbar);
        setupDiscipulosList();
        // setDummyContent();


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.d("D", "Busco esto + " + query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.d("D", "Esta buscando: " + newText);
//                return false;
//            }
//        });
        showToolbar(toolbar, "", false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ApiAdapter.getApiService().get_discipulos(ID_MIEMBRO, this);
    }

    public void setupDiscipulosList() {
        mDiscipulosList.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNAS));
        mDiscipulosList.setAdapter(adapter);
        mDiscipulosList.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.integer.offset));
    }

    private void setDummyContent () {
        ArrayList<Discipulo> discipulos = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            discipulos.add(new Discipulo("Discipulo " + i, "Discipulo " + i));
        }

        adapter.addAll(discipulos);
    }

    @Override
    public void success(DiscipulosResponse discipulosResponse, Response response) {
        adapter.addAll(discipulosResponse.get_discipulos());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("E", "error desde retrofit en discipulos");
        error.printStackTrace();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discipulos, menu);
        super.onCreateOptionsMenu(menu, inflater);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
//        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = searchView.findViewById(searchPlateId);
//        if (searchPlate != null) {
//            searchPlate.setBackgroundColor(Color.DKGRAY);
//            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//            TextView textView = (TextView) searchPlate.findViewById(searchTextId);
//            if (textView != null) {
//                textView.setTextColor(Color.WHITE);
//                textView.setHintTextColor(Color.WHITE);
//            }
//        }
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

    public void showToolbar(@NonNull View view, String title, boolean upButton) {
        // Toolbar toolbar = (Toolbar) view.findViewById(R.id.actiontoolbal);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (title != null && !title.isEmpty()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
