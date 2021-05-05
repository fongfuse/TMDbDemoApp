package com.fongfuse.tmdbdemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(val items: List<Movie>?) : Parcelable
