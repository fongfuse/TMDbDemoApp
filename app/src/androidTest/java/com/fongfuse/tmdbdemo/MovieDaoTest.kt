package com.fongfuse.tmdbdemo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fongfuse.tmdbdemo.data.local.AppDatabase
import com.fongfuse.tmdbdemo.data.local.MovieDao
import com.fongfuse.tmdbdemo.model.Movie
import com.fongfuse.tmdbdemo.model.MovieResponse
import com.fongfuse.tmdbdemo.util.ConfigUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: AppDatabase

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        movieDao = db.movieDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun insertAndLoadMovieListTest() = runBlocking {
        val response = mockResponse().items!!
        movieDao.addMovieList(response)

        val loadFromDB = movieDao.getMovieList()
        Assert.assertEquals(loadFromDB.toString(), response.toString())
    }

    @Test
    fun LoadMovieListEmptyTest() = runBlocking {
        val loadFromDB = movieDao.getMovieList()
        Assert.assertEquals(loadFromDB, listOf<Movie>())
    }

    fun mockResponse(): MovieResponse {
        val movieItem = Movie(
                id_car = 1,
                id = 1,
                title = "movie1",
                poster_path = ConfigUtil.IMAGE_URL  + "xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
                overview = "overview1",
                vote_average = 5.7f
        )
        return MovieResponse(
                items = listOf(movieItem))
    }
}