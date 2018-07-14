package com.intkhabahmed.popularmoviesstage2.networking;

import com.intkhabahmed.popularmoviesstage2.model.CastResult;
import com.intkhabahmed.popularmoviesstage2.model.MovieResult;
import com.intkhabahmed.popularmoviesstage2.model.ReviewResult;
import com.intkhabahmed.popularmoviesstage2.model.TrailerResult;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    @GET("movie/{filter}")
    Call<MovieResult> getMoviesByPreference(@Path("filter") String filter, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> getReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/casts")
    Call<CastResult> getCasts(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResult> getTrailers(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
