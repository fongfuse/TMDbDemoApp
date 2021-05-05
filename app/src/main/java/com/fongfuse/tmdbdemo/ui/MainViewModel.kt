package com.fongfuse.tmdbdemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fongfuse.tmdbdemo.model.Movie
import com.fongfuse.tmdbdemo.model.Result
import com.fongfuse.tmdbdemo.repository.CarRepository
import com.fongfuse.tmdbdemo.ui.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel constructor(private var carRepository: CarRepository) : BaseViewModel() {

    private val _items = MutableLiveData<List<Movie>>()
    val items: LiveData<List<Movie>> = _items

    fun getAllCars() {
        viewModelScope.launch {
            carRepository.getAllCars()
                .onStart { showLoading() }
                .onCompletion { hideLoading() }
                .collect {
                    when (it) {
                        is Result.SUCCESS -> _items.value = it.data!!
                        is Result.ERROR -> showError(it.error!!)
                        is Result.NetworkError -> showNetworkError()
                    }
                }
        }
    }
}
