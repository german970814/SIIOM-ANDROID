package com.ingeniartesoft.siiom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DiscipulosAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3, container, false);
        mDiscipulosList = (RecyclerView) v.findViewById(R.id.discipulos_list);
        ID_MIEMBRO = getArguments().getInt("ID_MIEMBRO", 1);
        setupDiscipulosList();
        // setDummyContent();
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


}
