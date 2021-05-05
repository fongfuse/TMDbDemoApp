package com.fongfuse.tmdbdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fongfuse.tmdbdemo.model.Movie

@Database(
    entities = [Movie::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}