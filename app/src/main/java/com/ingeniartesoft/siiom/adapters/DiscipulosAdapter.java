package com.ingeniartesoft.siiom.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingeniartesoft.siiom.DiscipuloDetailActivity;
import com.ingeniartesoft.siiom.R;
import com.ingeniartesoft.siiom.models.Discipulo;
import com.ingeniartesoft.siiom.ui.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by german on 13/09/16.
 */
public class DiscipulosAdapter extends RecyclerView.Adapter<DiscipulosAdapter.DiscipulosViewHolder> {
    private Context context;
    private ArrayList<Discipulo> discipulos;
    private ArrayList<Discipulo> _discipulos;

    public DiscipulosAdapter (Context context) {
        this.context = context;
        this.discipulos = new ArrayList<>();
        this._discipulos = new ArrayList<>();
    }

    @Override
    public DiscipulosViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_discipulo, viewGroup, false);

        return new DiscipulosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscipulosViewHolder holder, int position) {
        final Discipulo currentDiscipulo = discipulos.get(position);

        holder.setNombre_discipulo(currentDiscipulo.getNombre());
        holder.setImagen_discipulo(currentDiscipulo.getImagen());

        // es mejor poner los eventos aqui, ya que solo hay un discipulo
        holder.imagen_discipulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiscipuloDetailActivity.class);
                intent.putExtra("DISCIPULO_ID", currentDiscipulo.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return discipulos.size();
    }

    public void addAll(@NonNull ArrayList<Discipulo> discipulos) {
        if (discipulos == null) {
            throw new NullPointerException("The items cannot be null");
        }
        if (this.discipulos.size() > 0) {
            this.discipulos.clear();
        }
        this.discipulos.addAll(discipulos);
        if (this._discipulos.isEmpty()) {
            this._discipulos.addAll(discipulos);
        }
//        notifyItemRangeInserted(getItemCount() - 1, discipulos.size());
        notifyDataSetChanged();
    }

    public void filter(String text){
        this.discipulos.clear();
        if (text.isEmpty()) {
            addAll(_discipulos);
        } else {
            text = text.toLowerCase();
            ArrayList<Discipulo> recursive = new ArrayList<>();
            for (Discipulo discipulo: _discipulos) {
                Log.d("F", discipulo.getNombre());
                if (discipulo.getNombre().toLowerCase().contains(text)) {
                    recursive.add(discipulo);
                }
            }
            addAll(recursive);
        }
        notifyDataSetChanged();
    }

    public class DiscipulosViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_discipulo;
        ImageView imagen_discipulo;

        public DiscipulosViewHolder(View itemView) {
            super(itemView);

            nombre_discipulo = (TextView) itemView.findViewById(R.id.txt_name);
            imagen_discipulo = (ImageView) itemView.findViewById(R.id.id_discipulo);
        }

        public void setNombre_discipulo (String nombre_discipulo) {
            this.nombre_discipulo.setText(nombre_discipulo);
        }

        public void setImagen_discipulo (String imagen_discipulo) {
            Picasso.with(context).load(Constants.URL_BASE + imagen_discipulo).into(this.imagen_discipulo);
        }

        public String getNombre_discipulo () {
            return nombre_discipulo.getText().toString();
        }
    }
}
