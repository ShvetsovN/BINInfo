package com.example.bininfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.data.CardInfoRepository
import com.example.bininfo.model.BinHistory
import com.example.bininfo.model.CardInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val cardInfoRepository: CardInfoRepository
) : ViewModel() {

    val history: LiveData<List<BinHistory>> = cardInfoRepository.history

    fun loadCard(bin: String, onResult: (CardInfo?) -> Unit) {
        viewModelScope.launch {
            val result = cardInfoRepository.getCardFromServerByBin(bin)
            onResult(result)
        }
    }
}