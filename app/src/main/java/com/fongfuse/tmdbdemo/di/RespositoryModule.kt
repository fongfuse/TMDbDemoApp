package com.fongfuse.tmdbdemo.di

import com.fongfuse.tmdbdemo.repository.CarRepository
import com.fongfuse.tmdbdemo.repository.CarRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<CarRepository> { CarRepositoryImpl(get(), get()) }
}