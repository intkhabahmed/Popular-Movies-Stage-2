package com.intkhabahmed.popularmoviesstage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.intkhabahmed.popularmoviesstage2.model.MovieResult;
import com.intkhabahmed.popularmoviesstage2.networking.ApiClient;
import com.intkhabahmed.popularmoviesstage2.networking.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    private static MovieRepository mMovieRepository;

    public static MovieRepository getInstance() {
        if (mMovieRepository == null) {
            mMovieRepository = new MovieRepository();
        }
        return mMovieRepository;
    }

    public LiveData<MovieResult> getMovies(String filter, String apiKey) {
        final MutableLiveData<MovieResult> result = new MutableLiveData<>();
        ApiClient.getInstance().create(WebService.class)
                .getMoviesByPreference(filter, apiKey)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                        result.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.v(MovieRepository.class.getName(), "error: " + t.getMessage());
                    }
                });
        return result;
    }
}
