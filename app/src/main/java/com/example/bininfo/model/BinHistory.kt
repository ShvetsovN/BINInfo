package com.example.bininfo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bin: String,
    val bank: Bank,
    val brand: String,
    val country: Country,
    val number: Number,
    val prepaid: Boolean,
    val scheme: String,
    val type: String
)
