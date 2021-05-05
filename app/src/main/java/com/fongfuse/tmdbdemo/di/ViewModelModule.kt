package com.fongfuse.tmdbdemo.di

import com.fongfuse.tmdbdemo.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel(get()) }
}