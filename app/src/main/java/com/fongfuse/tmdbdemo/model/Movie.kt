package com.fongfuse.tmdbdemo.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "car_table")
data class Movie(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car")
        val id_car: Int,
        @ColumnInfo(name = "id")
        val id: Int,
        @ColumnInfo(name = "title")
        val title: String?,
        @ColumnInfo(name = "poster_path")
        val poster_path: String?,
        @ColumnInfo(name = "overview")
        val overview: String?,
        @ColumnInfo(name = "vote_average")
        val vote_average: Float?
) : Parcelable
