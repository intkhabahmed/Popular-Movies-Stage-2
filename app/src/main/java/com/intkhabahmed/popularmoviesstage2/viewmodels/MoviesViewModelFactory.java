package com.intkhabahmed.popularmoviesstage2.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class MoviesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String filter;
    private String apiKey;

    public MoviesViewModelFactory(String filter, String apiKey) {
        this.filter = filter;
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MoviesViewModel(filter, apiKey);
    }
}
