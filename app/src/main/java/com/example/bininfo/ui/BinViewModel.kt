package com.example.bininfo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.data.BinRepository
import com.example.bininfo.model.BinHistory
import com.example.bininfo.model.BinInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BinViewModel @Inject constructor(
    private val binRepository: BinRepository
) : ViewModel() {

    private val _binInfo = MutableStateFlow<BinInfo?>(null)
    open val binInfo: StateFlow<BinInfo?>
        get() = _binInfo.asStateFlow()

    private val _history = MutableStateFlow<List<BinHistory>>(emptyList())
    val history: StateFlow<List<BinHistory>>
        get() = _history.asStateFlow()

    init {
        loadHistory()
    }

    fun getBinInfo(bin: String) {
        if(bin.length !in 6..8 || bin.any{ !it.isDigit()}) {
            Log.e("BinViewModel", "Ошибка: BIN должен содержать от 6 до 8 цифр")
        }
        viewModelScope.launch {
            val result = binRepository.getBinInfo(bin)
            result?.let {
                _binInfo.value = it

                val binHistory = BinHistory(
                    bin = bin,
                    bank = it.bank,
                    brand = it.brand,
                    country = it.country,
                    number = it.number,
                    prepaid = it.prepaid,
                    scheme = it.scheme,
                    type = it.type
                )

                binRepository.saveBinInfo(binHistory)
                loadHistory()
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _history.value = binRepository.getHistory()
        }
    }
}
