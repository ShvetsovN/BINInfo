package com.example.bininfo.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.bininfo.model.BinHistory
import com.example.bininfo.model.CardInfo
import javax.inject.Inject

class CardInfoRepository @Inject constructor(
    private val cardInfoDao: CardInfoDao,
    private val cardInfoApiService: CardInfoApiService,
) {

    val history: LiveData<List<BinHistory>> = cardInfoDao.getCard()

    suspend fun getCardFromServerByBin(bin: String) : CardInfo? {
        return try {
            val response = cardInfoApiService.getCardInfo(bin)
            if(response.isSuccessful) {
                response.body()?.let { responseBody ->
                    val cardInfo = CardInfo(
                        bank = responseBody.bank,
                        brand = responseBody.brand,
                        country = responseBody.country,
                        cardNumber = responseBody.cardNumber,
                        prepaid = responseBody.prepaid,
                        scheme = responseBody.scheme,
                        type = responseBody.type,
                    )

                    val binHistory = BinHistory(
                        bin = bin,
                        bank = responseBody.bank,
                        brand = responseBody.brand,
                        country = responseBody.country,
                        cardNumber = responseBody.cardNumber,
                        prepaid = responseBody.prepaid,
                        scheme = responseBody.scheme,
                        type = responseBody.type,
                    )

                    cardInfoDao.insert(binHistory)
                    cardInfo
                }
            } else {
                Log.e("CardInfoRepository", "Ошибка запроса: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("CardInfoRepository", "Ошибка запроса: ${e.message}")
            null
        }
    }
}
