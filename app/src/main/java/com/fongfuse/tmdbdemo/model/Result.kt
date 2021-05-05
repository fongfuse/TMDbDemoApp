package com.fongfuse.tmdbdemo.model

sealed class Result<out T : Any> {
    data class SUCCESS<out T: Any>(val data: T?) : Result<T>()
    data class ERROR(val error: String?, val code: Int?): Result<Nothing>()
    object NetworkError: Result<Nothing>()
}