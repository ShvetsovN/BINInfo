package com.example.bininfo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bininfo.model.BinHistory
import com.example.bininfo.model.CardInfo

@Dao
interface CardInfoDao {
    @Query("SELECT * FROM bin_history ORDER BY id DESC")
    fun getCard(): LiveData<List<BinHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: BinHistory)
}
