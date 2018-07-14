package com.intkhabahmed.popularmoviesstage2.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.intkhabahmed.popularmoviesstage2.database.MovieRepository;
import com.intkhabahmed.popularmoviesstage2.model.Cast;
import com.intkhabahmed.popularmoviesstage2.model.Review;
import com.intkhabahmed.popularmoviesstage2.model.Trailer;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    private LiveData<List<Review>> reviews;
    private LiveData<List<Cast>> casts;
    private LiveData<Integer> favourite;
    private LiveData<List<Trailer>> trailers;

    DetailsViewModel(int movieId, String apiKey) {
        if (reviews == null) {
            reviews = MovieRepository.getInstance().getReviews(movieId, apiKey);
        }
        if (casts == null) {
            casts = MovieRepository.getInstance().getCasts(movieId, apiKey);
        }
        if (favourite == null) {
            favourite = MovieRepository.getInstance().isFavourite(movieId);
        }
        if (trailers == null) {
            trailers = MovieRepository.getInstance().getTrailers(movieId, apiKey);
        }
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public LiveData<List<Cast>> getCasts() {
        return casts;
    }

    public LiveData<Integer> getFavourite() {
        return favourite;
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }
}
