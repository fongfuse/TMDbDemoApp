package com.fongfuse.tmdbdemo.data.remote

import com.fongfuse.tmdbdemo.model.MovieResponse
import com.fongfuse.tmdbdemo.util.ConfigUtil
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list/{list_id}")
    suspend fun getAllMovie(
            @Path("list_id")
            listId: Int = 1,
            @Query("api_key")
            apiKey: String = ConfigUtil.API_KEY
    )
            : Response<MovieResponse>
}