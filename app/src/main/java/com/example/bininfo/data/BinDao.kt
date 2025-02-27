package com.example.bininfo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bininfo.model.BinHistory

@Dao
interface BinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(binHistory: BinHistory)

    @Query("SELECT * FROM bin_history ORDER BY id DESC")
    suspend fun getAll(): List<BinHistory>
}
