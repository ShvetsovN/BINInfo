package com.example.bininfo.data

import androidx.room.TypeConverter
import com.example.bininfo.model.Bank
import com.example.bininfo.model.Country
import com.example.bininfo.model.Number
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromBank(bank: Bank): String {
        return json.encodeToString(bank)
    }

    @TypeConverter
    fun toBank(value: String): Bank {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromCountry(country: Country): String {
        return json.encodeToString(country)
    }

    @TypeConverter
    fun toCountry(value: String): Country {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromNumber(number: Number): String {
        return json.encodeToString(number)
    }

    @TypeConverter
    fun toNumber(value: String): Number {
        return json.decodeFromString(value)
    }
}
