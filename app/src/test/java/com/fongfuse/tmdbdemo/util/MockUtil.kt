package com.fongfuse.tmdbdemo.util

import com.fongfuse.tmdbdemo.model.Movie
import com.fongfuse.tmdbdemo.model.MovieResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object MockUtil {

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

    fun mockResponseFailed(): Response<MovieResponse> {
        return Response.error(
                "error_response_test".toResponseBody("application/json".toMediaTypeOrNull()),
                okhttp3.Response.Builder()
                    .code(404)
                    .message("failed")
                    .request(Request.Builder().url("http://test-url/").build())
                    .protocol(Protocol.HTTP_1_1)
                    .build())
    }
}
