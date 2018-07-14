package com.intkhabahmed.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResult {
    @SerializedName("results")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
