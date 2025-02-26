package com.example.bininfo.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val alpha2: String,
    val currency: String,
    val emoji: String,
    val latitude: Int,
    val longitude: Int,
    val name: String,
    val numeric: String
)