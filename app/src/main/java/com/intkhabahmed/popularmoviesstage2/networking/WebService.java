package com.intkhabahmed.popularmoviesstage2.networking;

import com.intkhabahmed.popularmoviesstage2.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    @GET("movie/{filter}")
    Call<MovieResult> getMoviesByPreference(@Path("filter") String filter, @Query("api_key") String apiKey);
}
