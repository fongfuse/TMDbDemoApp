package com.fongfuse.tmdbdemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fongfuse.tmdbdemo.MainCoroutinesRule
import com.fongfuse.tmdbdemo.data.local.MovieDao
import com.fongfuse.tmdbdemo.data.remote.ApiService
import com.fongfuse.tmdbdemo.repository.CarRepository
import com.fongfuse.tmdbdemo.repository.CarRepositoryImpl
import com.fongfuse.tmdbdemo.ui.MainViewModel
import com.fongfuse.tmdbdemo.util.MockUtil
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var repository: CarRepository
    private lateinit var viewModel: MainViewModel
    private val api: ApiService = mockk()
    private val movieDao: MovieDao = mockk()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        repository = CarRepositoryImpl(api, movieDao)
        viewModel = spyk(MainViewModel(repository))
    }

    @Test
    fun callCarListSuccess() = runBlocking {

        val response = MockUtil.mockResponse()

        coEvery { movieDao.getMovieList() } returns null
        coEvery { api.getAllMovie() } returns Response.success(response)
        coEvery { movieDao.addMovieList(response.items!!) } returns Unit

        val loading = mutableListOf<Boolean>()
        viewModel.loading.observeForever { loading.add(it) }

        viewModel.getAllCars()

        verify { viewModel.showLoading() }
        verify { viewModel.hideLoading() }

        Assert.assertEquals(loading[0], true)
        Assert.assertEquals(loading[1], false)
        Assert.assertEquals(viewModel.items.value, response.items!!)

    }

    @Test
    fun callCarListFailed() = runBlockingTest {

        val response = MockUtil.mockResponseFailed()

        coEvery { movieDao.getMovieList() } returns null
        coEvery { api.getAllMovie() } returns response

        val loading = mutableListOf<Boolean>()
        viewModel.loading.observeForever { loading.add(it) }

        viewModel.getAllCars()

        verify { viewModel.showLoading() }
        verify { viewModel.hideLoading() }
        Assert.assertEquals(loading[0], true)
        Assert.assertEquals(loading[1], false)
        Assert.assertEquals(viewModel.items.value, null)
    }
}
