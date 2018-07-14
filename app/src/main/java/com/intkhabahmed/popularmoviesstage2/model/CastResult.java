package com.intkhabahmed.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResult {
    @SerializedName("cast")
    private List<Cast> casts;

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }
}
