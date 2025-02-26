package com.example.bininfo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val bin: String,
    @ColumnInfo val bank: Bank?,
    @ColumnInfo val brand: String?,
    @ColumnInfo val country: Country?,
    @ColumnInfo(name = "number") val cardNumber: CardNumber?,
    @ColumnInfo val prepaid: Boolean?,
    @ColumnInfo val scheme: String?,
    @ColumnInfo val type: String?
)
