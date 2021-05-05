package com.fongfuse.tmdbdemo.repository

import com.fongfuse.tmdbdemo.data.local.MovieDao
import com.fongfuse.tmdbdemo.data.remote.ApiService
import com.fongfuse.tmdbdemo.model.Movie
import com.fongfuse.tmdbdemo.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface CarRepository {
    suspend fun getAllCars(): Flow<Result<List<Movie>>>
}

class CarRepositoryImpl constructor(
        private val api: ApiService,
        private var movieDao: MovieDao
) : CarRepository {

    override suspend fun getAllCars(): Flow<Result<List<Movie>>> {

        return flow {
            movieDao.getMovieList()?.let {
                emit(Result.SUCCESS(it))
            }
            val response = api.getAllMovie()
            if (response.isSuccessful) {
                movieDao.addMovieList((response.body()?.items ?: listOf()))
                emit(Result.SUCCESS(response.body()?.items ?: listOf()))

            } else {
                emit(Result.ERROR(response.message(), response.code()))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Result.NetworkError)
        }.flowOn(Dispatchers.IO)
    }

}