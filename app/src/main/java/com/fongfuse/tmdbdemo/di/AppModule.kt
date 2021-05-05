package com.fongfuse.tmdbdemo.di

import android.app.Application
import androidx.room.Room
import com.fongfuse.tmdbdemo.data.remote.ApiService
import com.fongfuse.tmdbdemo.data.local.AppDatabase
import com.fongfuse.tmdbdemo.data.local.MovieDao
import com.fongfuse.tmdbdemo.util.ConfigUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
}

val serviceModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
}

val databaseModule = module {
    single { provideAppDatabase(get()) }
    single { provideCarDao(get()) }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

private fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient().newBuilder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()

private fun provideRetrofit(
        okHttpClient: OkHttpClient
) =
    Retrofit.Builder()
        .baseUrl(ConfigUtil.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun provideAppDatabase(app: Application): AppDatabase {
    return Room.databaseBuilder(app, AppDatabase::class.java, "movie_database").build()
}

private fun provideCarDao(appDatabase: AppDatabase): MovieDao {
    return appDatabase.movieDao()
}
