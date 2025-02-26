package com.example.bininfo.model

import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    val city: String,
    val name: String,
    val phone: String,
    val url: String
)