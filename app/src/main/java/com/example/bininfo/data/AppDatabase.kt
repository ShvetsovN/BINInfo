package com.example.bininfo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bininfo.model.BinHistory

@Database(
    entities = [BinHistory::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun binDao(): BinDao
}
