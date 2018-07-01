package com.intkhabahmed.popularmoviesstage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.intkhabahmed.popularmoviesstage2.model.FavouriteMovie;
import com.intkhabahmed.popularmoviesstage2.model.Movie;

import java.util.List;

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE criteria = :criteria")
    LiveData<List<Movie>> getMoviesByCriteria(String criteria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("DELETE FROM movies WHERE criteria = :criteria")
    void deleteAllByCriteria(String criteria);

    @Query("SELECT * FROM favourites")
    LiveData<List<FavouriteMovie>> getFavouriteMovies();

    @Query("SELECT COUNT(movie_id) FROM favourites WHERE movie_id = :movieId")
    LiveData<Integer> isFavourite(int movieId);

    @Insert
    void insertFavouriteMovie(FavouriteMovie favouriteMovie);

    @Query("DELETE FROM favourites WHERE movie_id = :movieId")
    void deleteFavouriteMovie(int movieId);

}
