package com.example.bininfo.model

import kotlinx.serialization.Serializable

@Serializable
data class CardNumber(
    val length: Int,
    val luhn: Boolean
)