package com.ingeniartesoft.siiom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingeniartesoft.siiom.R;
import com.ingeniartesoft.siiom.models.Discipulo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by german on 13/09/16.
 */
public class DiscipulosAdapter extends RecyclerView.Adapter<DiscipulosAdapter.DiscipulosViewHolder> {
    Context context;
    ArrayList<Discipulo> discipulos;

    public DiscipulosAdapter (Context context) {
        this.context = context;
        this.discipulos = new ArrayList<>();
    }

    @Override
    public DiscipulosViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_discipulo, viewGroup, false);

        return new DiscipulosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscipulosViewHolder holder, int position) {
        Discipulo currentDiscipulo = discipulos.get(position);

        holder.setNombre_discipulo(currentDiscipulo.getNombre());
        holder.setImagen_discipulo(currentDiscipulo.getImagen());
    }

    @Override
    public int getItemCount() {
        return discipulos.size();
    }

    public void addAll(@NonNull ArrayList<Discipulo> discipulos) {
        if (discipulos == null) {
            throw new NullPointerException("The items cannot be null");
        }
        this.discipulos.addAll(discipulos);
//        notifyItemRangeInserted(getItemCount() - 1, discipulos.size());
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
            Picasso.with(context).load("http://10.0.2.2:7000" + imagen_discipulo).into(this.imagen_discipulo);
        }

        public String getNombre_discipulo () {
            return nombre_discipulo.getText().toString();
        }
    }
}
