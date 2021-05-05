package com.fongfuse.tmdbdemo.repository

import com.fongfuse.tmdbdemo.MainCoroutinesRule
import com.fongfuse.tmdbdemo.data.local.MovieDao
import com.fongfuse.tmdbdemo.data.remote.ApiService
import com.fongfuse.tmdbdemo.model.Movie
import com.fongfuse.tmdbdemo.model.Result
import com.fongfuse.tmdbdemo.util.MockUtil
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private lateinit var repository: CarRepository

    private val api: ApiService = mockk()
    private val movieDao: MovieDao = mockk()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository = CarRepositoryImpl(api, movieDao)
    }

    @Test
    fun fetchCarListFromNetworkSuccessAndNoDataInDB() = runBlocking {

        val response = MockUtil.mockResponse()

        coEvery { movieDao.getMovieList() } returns null
        coEvery { api.getAllMovie() } returns Response.success(response)
        coEvery { movieDao.addMovieList(response.items!!) } returns Unit

        val result = ArrayList<Result<List<Movie>>>()

        repository.getAllCars().collect {
            result.add(it) //add result when collect called
        }

        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], Result.SUCCESS(response.items!!))
    }

    @Test
    fun fetchCarListFromNetworkFailedAndNoDataInDB() = runBlocking {

        val response = MockUtil.mockResponseFailed()

        coEvery { movieDao.getMovieList() } returns null
        coEvery { api.getAllMovie() } returns response

        val result = ArrayList<Result<List<Movie>>>()

        repository.getAllCars().collect {
            result.add(it) //add result when collect called
        }

        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], Result.ERROR( "failed", 404))
    }

    @Test
    fun fetchCarListFromNetworkSuccessAndAlreadyDataInDB() = runBlocking {

        val response = MockUtil.mockResponse()

        coEvery { movieDao.getMovieList() } returns response.items!!
        coEvery { api.getAllMovie() } returns Response.success(response)
        coEvery { movieDao.addMovieList(response.items!!) } returns Unit

        val result = ArrayList<Result<List<Movie>>>()

        repository.getAllCars().collect {
            result.add(it) //add result when collect called
        }

        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0], Result.SUCCESS(response.items!!))
        Assert.assertEquals(result[1], Result.SUCCESS(response.items!!))
    }

    @Test
    fun fetchCarListFromNetworkFailedAndAlreadyDataInDB() = runBlocking {

        val responseSuccess = MockUtil.mockResponse()
        val responseFailed = MockUtil.mockResponseFailed()

        coEvery { movieDao.getMovieList() } returns responseSuccess.items!!
        coEvery { api.getAllMovie() } returns responseFailed

        val result = ArrayList<Result<List<Movie>>>()

        repository.getAllCars().collect {
            result.add(it) //add result when collect called
        }

        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0], Result.SUCCESS(responseSuccess.items!!))
        Assert.assertEquals(result[1], Result.ERROR( "failed", 404))
    }
}
