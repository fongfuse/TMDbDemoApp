package com.fongfuse.tmdbdemo.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean> = _networkError

    open fun showLoading() {
        _loading.value = true
    }

    open fun hideLoading() {
        _loading.value = false
    }

    open fun showError(message: String) {
        _error.value = message
    }

    open fun showNetworkError() {
        _networkError.value = true
    }


}