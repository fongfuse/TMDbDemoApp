package com.fongfuse.tmdbdemo.data.local

import androidx.room.*
import com.fongfuse.tmdbdemo.model.Movie

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addMovieList(movieList: List<Movie>)

  @Query("SELECT * FROM car_table")
  suspend fun getMovieList(): List<Movie>?

  @Delete
  suspend fun deleteAll(movieList: List<Movie>)
}
