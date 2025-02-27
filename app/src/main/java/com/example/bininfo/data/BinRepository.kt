package com.example.bininfo.data

import android.util.Log
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
            if (response.isSuccessful) {
                Log.e("BinRepository", "Тело запроса: ${response.body()}")
                response.body()
            } else {
                Log.e("BinRepository", "Ошибка запроса: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("BinRepository", "Ошибка сети: ${e.message}")
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
