package com.example.bininfo.model

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class CardInfo(
    @ColumnInfo val bank: Bank,
    @ColumnInfo val brand: String,
    @ColumnInfo val country: Country,
    @ColumnInfo(name = "number") val cardNumber: CardNumber,
    @ColumnInfo val prepaid: Boolean,
    @ColumnInfo val scheme: String,
    @ColumnInfo val type: String
)