package com.example.bininfo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bininfo.model.Bank
import com.example.bininfo.model.BinHistory
import com.example.bininfo.model.Country
import com.example.bininfo.model.Number
import com.example.bininfo.ui.BinViewModel
import com.example.bininfo.ui.theme.BINInfoTheme
import com.example.bininfo.ui.theme.mainCardColors

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: BinViewModel = hiltViewModel(),
) {
    val history by viewModel.history.collectAsState(emptyList())

    Scaffold()
    {
        if (history.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "История пуста",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
            ) {
                items(history) { item ->
                    BinHistory(item)
                }
            }
        }
    }
}

@Composable
fun BinHistory(item: BinHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(mainCardColors)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "BIN: ${item.bin}", fontWeight = FontWeight.Bold)
                Text(text = "${item.brand} (${item.type})", fontWeight = FontWeight.Bold)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item.country?.name?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
                item.country?.emoji?.let { Text(text = it, fontSize = 24.sp) }
            }
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = if (item.prepaid == true) "Предоплата: Да" else "Предоплата: Нет",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Длина номера карты: ${item.number?.length}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                    )
                    Text(
                        text = if (item.number?.luhn == true) "Алгоритм Луна: Да" else "Алгоритм Луна: Нет",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                    )
                }
            }

            HorizontalDivider()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Банк: ${item.bank?.name}", fontWeight = FontWeight.Medium)
                Text(text = "Валюта: ${item.country?.currency}", color = Color.Gray)
            }

            Text(text = "Город: ${item.bank?.city}", fontWeight = FontWeight.Medium)

            Text(
                text = "Широта: ${item.country?.latitude} Долгота: ${item.country?.longitude}"
            )
            Text(
                text = "Телефон: ${item.bank?.phone}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
            )
            Text(
                text = "Сайт: ${item.bank?.url}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    BINInfoTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HistoryScreen(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BinHistoryPreview() {
    val sampleItem = BinHistory(
        bin = "1234 0056",
        brand = "Visa",
        type = "Credit",
        prepaid = false,
        number = Number(length = 16, luhn = true),
        country = Country(
            emoji = "🇷🇺", name = "Russia", currency = "RUB",
            latitude = 55, longitude = 37,
            alpha2 = "DK",
            numeric = "208"
        ),
        bank = Bank(
            name = "Sberbank",
            city = "Moscow",
            phone = "+74956660000",
            url = "www.sberbank.ru"
        ),
        scheme = "visa"
    )
    BINInfoTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            BinHistory(item = sampleItem)
        }
    }
}
