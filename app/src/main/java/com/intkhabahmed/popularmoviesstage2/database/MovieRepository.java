package com.intkhabahmed.popularmoviesstage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.intkhabahmed.popularmoviesstage2.model.Movie;
import com.intkhabahmed.popularmoviesstage2.model.MovieResult;
import com.intkhabahmed.popularmoviesstage2.networking.ApiClient;
import com.intkhabahmed.popularmoviesstage2.networking.WebService;
import com.intkhabahmed.popularmoviesstage2.utils.AppConstants;
import com.intkhabahmed.popularmoviesstage2.utils.Global;

import java.util.List;

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

    public LiveData<MovieResult> getMovies(String criteria, String apiKey) {
        final MutableLiveData<MovieResult> result = new MutableLiveData<>();
        List<Movie> movies = Global.getDbInstance().movieDao().getMoviesByCriteria(criteria).getValue();
        if (movies != null && movies.size() > 0) {
            result.setValue(new MovieResult(movies));
        } else {
            ApiClient.getInstance().create(WebService.class)
                    .getMoviesByPreference(criteria, apiKey)
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
        }
        return result;
    }
}
