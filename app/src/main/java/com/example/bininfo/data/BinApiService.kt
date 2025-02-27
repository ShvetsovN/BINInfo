package com.example.bininfo.data

import com.example.bininfo.model.BinInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BinApiService {
    @GET("{bin}")
    suspend fun getBinInfo(
        @Path("bin") bin: String,
        @Header("Accept-Version") version: String = "3"
        ): Response<BinInfo>
}
