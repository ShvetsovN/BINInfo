package com.example.bininfo.data

import com.example.bininfo.model.BinHistory
import com.example.bininfo.model.BinInfo
import javax.inject.Inject

class BinRepository @Inject constructor(
    private val binDao: BinDao,
    private val binApi: BinApiService,
) {

    suspend fun getBinInfo(bin: String): BinInfo? {
        return try {
            val response = binApi.getBinInfo(bin)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveBinInfo(binHistory: BinHistory) {
        binDao.insert(binHistory)
    }

    suspend fun getHistory(): List<BinHistory> {
        return binDao.getAll()
    }

}
