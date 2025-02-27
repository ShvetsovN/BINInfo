package com.example.bininfo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bin: String,
    val bank: Bank? = null,
    val brand: String? = "",
    val country: Country? = null,
    val number: Number? = null,
    val prepaid: Boolean? = false,
    val scheme: String? = "",
    val type: String? = ""
)
