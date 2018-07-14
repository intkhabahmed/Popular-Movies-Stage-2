package com.intkhabahmed.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("character")
    private String movieCharacter;
    @SerializedName("name")
    private String originalName;
    @SerializedName("profile_path")
    private String profilePath;

    public String getMovieCharacter() {
        return movieCharacter;
    }

    public void setMovieCharacter(String movieCharacter) {
        this.movieCharacter = movieCharacter;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
