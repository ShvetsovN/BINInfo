package com.example.bininfo.data

import com.example.bininfo.model.CardInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CardInfoApiService {
    @GET("{bin}")
    suspend fun getCardInfo(
        @Path("bin") bin: String,
        @Header("Accept-Version") version: String = "3"
    ): Response<CardInfo>
}