package com.intkhabahmed.popularmoviesstage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intkhabahmed.popularmoviesstage2.model.Cast;
import com.intkhabahmed.popularmoviesstage2.model.CastResult;
import com.intkhabahmed.popularmoviesstage2.model.FavouriteMovie;
import com.intkhabahmed.popularmoviesstage2.model.Movie;
import com.intkhabahmed.popularmoviesstage2.model.MovieResult;
import com.intkhabahmed.popularmoviesstage2.model.Review;
import com.intkhabahmed.popularmoviesstage2.model.ReviewResult;
import com.intkhabahmed.popularmoviesstage2.model.Trailer;
import com.intkhabahmed.popularmoviesstage2.model.TrailerResult;
import com.intkhabahmed.popularmoviesstage2.networking.ApiClient;
import com.intkhabahmed.popularmoviesstage2.networking.WebService;
import com.intkhabahmed.popularmoviesstage2.utils.AppConstants;
import com.intkhabahmed.popularmoviesstage2.utils.AppExecutors;
import com.intkhabahmed.popularmoviesstage2.utils.Global;
import com.intkhabahmed.popularmoviesstage2.utils.NetworkUtils;

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

        if (criteria.equals(AppConstants.FAVOURITE_MOVIES)) {
            return getAllFavouriteMovies();
        }
        if (!NetworkUtils.getConnectivityStatus(Global.getInstance())) {
            return getAllMoviesFromDB(criteria);
        }
        ApiClient.getInstance().create(WebService.class)
                .getMoviesByPreference(criteria, apiKey)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                        result.setValue(response.body());
                        saveInDatabase(response.body().getMovies(), criteria);
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.v(MovieRepository.class.getSimpleName(), "error: " + t.getMessage());
                    }
                });
        return result;
    }

    public LiveData<List<Review>> getReviews(int movieId, String apiKey) {
        final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
        ApiClient.getInstance().create(WebService.class)
                .getReviews(movieId, apiKey)
                .enqueue(new Callback<ReviewResult>() {
                    @Override
                    public void onResponse(@NonNull Call<ReviewResult> call, @NonNull Response<ReviewResult> response) {
                        reviews.setValue(response.body().getReviews());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.v(MovieRepository.class.getSimpleName(), "error: " + t.getMessage());
                    }
                });
        return reviews;
    }

    public LiveData<List<Cast>> getCasts(int movieId, String apiKey) {
        final MutableLiveData<List<Cast>> casts = new MutableLiveData<>();
        ApiClient.getInstance().create(WebService.class)
                .getCasts(movieId, apiKey)
                .enqueue(new Callback<CastResult>() {
                    @Override
                    public void onResponse(@NonNull Call<CastResult> call, @NonNull Response<CastResult> response) {
                        casts.setValue(response.body().getCasts());
                    }

                    @Override
                    public void onFailure(@NonNull Call<CastResult> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.v(MovieRepository.class.getSimpleName(), "error: " + t.getMessage());
                    }
                });
        return casts;
    }

    public LiveData<List<Trailer>> getTrailers(int movieId, String apiKey) {
        final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
        ApiClient.getInstance().create(WebService.class)
                .getTrailers(movieId, apiKey)
                .enqueue(new Callback<TrailerResult>() {
                    @Override
                    public void onResponse(@NonNull Call<TrailerResult> call, @NonNull Response<TrailerResult> response) {
                        trailers.setValue(response.body().getTrailers());
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrailerResult> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.v(MovieRepository.class.getSimpleName(), "error: " + t.getMessage());
                    }
                });
        return trailers;
    }

    private LiveData<MovieResult> getAllMoviesFromDB(String criteria) {
        final MediatorLiveData<MovieResult> mediatorLiveData = new MediatorLiveData<>();
        final LiveData<List<Movie>> moviesLiveData = Global.getDbInstance().movieDao().getMoviesByCriteria(criteria);

        mediatorLiveData.addSource(moviesLiveData, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mediatorLiveData.removeSource(moviesLiveData);
                if (movies != null && !movies.isEmpty()) {
                    mediatorLiveData.setValue(new MovieResult(movies));
                } else {
                    mediatorLiveData.setValue(null);
                }
            }
        });
        return mediatorLiveData;
    }

    private void saveInDatabase(final List<Movie> movies, final String sortCriteria) {
        for (Movie movie : movies) {
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

    private LiveData<MovieResult> getAllFavouriteMovies() {
        final MediatorLiveData<MovieResult> mediatorLiveData = new MediatorLiveData<>();
        final LiveData<List<FavouriteMovie>> moviesLiveData = Global.getDbInstance().movieDao().getFavouriteMovies();

        mediatorLiveData.addSource(moviesLiveData, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovie> favouriteMovies) {
                mediatorLiveData.removeSource(moviesLiveData);
                if (favouriteMovies != null && !favouriteMovies.isEmpty()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(favouriteMovies);
                    List<Movie> movies = gson.fromJson(json, new TypeToken<List<Movie>>() {
                    }.getType());
                    mediatorLiveData.setValue(new MovieResult(movies));
                } else {
                    mediatorLiveData.setValue(null);
                }
            }
        });
        return mediatorLiveData;
    }
}
