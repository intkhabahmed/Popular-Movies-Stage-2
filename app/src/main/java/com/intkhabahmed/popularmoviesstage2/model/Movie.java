package com.intkhabahmed.popularmoviesstage2.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.intkhabahmed.popularmoviesstage2.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "movies")
@TypeConverters(DateUtils.class)
public class Movie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int rowId;
    @SerializedName("id")
    @ColumnInfo(name = "movie_id")
    private int movieId;
    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private Date releaseDate;
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterUrl;
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private float voteAverage;
    @SerializedName("overview")
    private String overview;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "criteria")
    private String criteria;

    @Ignore
    public Movie(int movieId, String originalTitle, Date releaseDate, String posterUrl, float voteAverage,
                 String overview, String backdropPath, String criteria) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.criteria = criteria;
    }

    public Movie(int rowId, int movieId, String originalTitle, Date releaseDate, String posterUrl, float voteAverage,
                 String overview, String backdropPath, String criteria) {
        this.rowId = rowId;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.criteria = criteria;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
}
