package com.ingeniartesoft.siiom.io.models;

import com.google.gson.annotations.SerializedName;
import com.ingeniartesoft.siiom.models.Discipulo;

import java.util.ArrayList;

/**
 * Created by german on 13/09/16.
 */
public class DiscipulosResponse {
    //Resultado
    @SerializedName("discipulos")
    DiscipulosResults results;

    public ArrayList<Discipulo> get_discipulos() {
        return results.discipulos;
    }

    private class DiscipulosResults {
        @SerializedName("discipulo")
        ArrayList<Discipulo> discipulos;
    }

}
