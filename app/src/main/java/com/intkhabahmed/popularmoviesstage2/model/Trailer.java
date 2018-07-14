package com.intkhabahmed.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("key")
    private String videoKey;

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }
}
