package com.intkhabahmed.popularmoviesstage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseIntArray;

import com.intkhabahmed.popularmoviesstage2.model.FavouriteMovie;
import com.intkhabahmed.popularmoviesstage2.model.Movie;
import com.intkhabahmed.popularmoviesstage2.model.MovieResult;
import com.intkhabahmed.popularmoviesstage2.networking.ApiClient;
import com.intkhabahmed.popularmoviesstage2.networking.WebService;
import com.intkhabahmed.popularmoviesstage2.utils.AppConstants;
import com.intkhabahmed.popularmoviesstage2.utils.AppExecutors;
import com.intkhabahmed.popularmoviesstage2.utils.Global;
import com.intkhabahmed.popularmoviesstage2.utils.NetworkUtils;

import java.util.ArrayList;
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

    public LiveData<MovieResult> getMovies(final String criteria, String apiKey) {
        final MutableLiveData<MovieResult> result = new MutableLiveData<>();

        if (!NetworkUtils.getConnectivityStatus(Global.getInstance())) {
            return getAllMoviesFromDB(criteria);
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

    private LiveData<MovieResult> getAllMoviesFromDB(String criteria) {
        final MediatorLiveData<MovieResult> mediatorLiveData = new MediatorLiveData<>();
        final LiveData<List<Movie>> moviesLiveData = Global.getDbInstance().movieDao().getMoviesByCriteria(criteria);

        mediatorLiveData.addSource(moviesLiveData, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mediatorLiveData.removeSource(moviesLiveData);
                if(movies != null && !movies.isEmpty()) {
                    mediatorLiveData.setValue(new MovieResult(movies));
                } else {
                    mediatorLiveData.setValue(null);
                }
            }
        });
        return mediatorLiveData;
    }

    public void saveInDatabase(final List<Movie> movies, final String sortCriteria) {
        for(Movie movie : movies) {
            movie.setCriteria(sortCriteria);
        }
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Global.getDbInstance().movieDao().deleteAllByCriteria(sortCriteria);
                Global.getDbInstance().movieDao().insertMovies(movies);
            }
        });
    }

    public LiveData<Integer> isFavourite(final int movieId) {
        return Global.getDbInstance().movieDao().isFavourite(movieId);
    }

    public void refreshFavouriteMovies(final FavouriteMovie favouriteMovie, boolean isFavourite) {
        Log.v("Favourite", isFavourite+"");
        if (isFavourite) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Global.getDbInstance().movieDao().deleteFavouriteMovie(favouriteMovie.getMovieId());
                }
            });
            return;
        }
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Global.getDbInstance().movieDao().insertFavouriteMovie(favouriteMovie);
            }
        });
    }
}
