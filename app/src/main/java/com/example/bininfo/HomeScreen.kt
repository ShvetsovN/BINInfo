package com.example.bininfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bininfo.model.Bank
import com.example.bininfo.model.CardInfo
import com.example.bininfo.model.CardNumber
import com.example.bininfo.model.Country
import com.example.bininfo.ui.CardInfoViewModel
import com.example.bininfo.ui.theme.mainCardColors

@Composable
fun HomeScreen(viewModel: CardInfoViewModel = hiltViewModel()) {
    var bin by remember { mutableStateOf("") }
    var cardInfo by remember { mutableStateOf<CardInfo?>(null) }
    val history by viewModel.history.observeAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CheckCardField(
            bin = bin,
            onBinChange = { bin = it }
        )

        FieldDescription()

        LookUpButton(
            onClick = {
                if (bin.isNotEmpty()) {
                    viewModel.loadCard(bin) { result ->
                        cardInfo = result
                    }
                }
            }
        )

        cardInfo?.let {
            CardInfo(it)
        } ?: Text(text = "Введите BIN для поиска", color = Color.Gray)
    }
}

@Composable
fun CheckCardField(bin: String, onBinChange: (String) -> Unit) {
    val cardCheckersRegex = Regex("""^\d{6}(\d{2})?$""")
    var errorState by remember { mutableStateOf("") }

    fun formatBinInput(input: String): String {
        val digits = input.filter { it.isDigit() }
        return digits.chunked(4).joinToString(" ")
    }

    OutlinedTextField(
        value = bin,
        modifier = Modifier
            .fillMaxWidth(),
        onValueChange = {
            val formattedInput = formatBinInput(it)
            onBinChange(formattedInput)

            errorState =
                if (cardCheckersRegex.matches(it.replace(" ", ""))) ""
                else "Некорректный номер"
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = "0000 0000",
                color = Color.Gray,
                fontSize = 16.sp,
            )
        },
        singleLine = true,
        label = {
            Text(
                text = errorState.ifEmpty { "BIN/IIN card" },
                fontSize = 16.sp
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onBinChange("")
                    errorState = ""
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Иконка очистки поля",
                )
            }
        },
        isError = errorState.isNotEmpty(),
    )
}

@Composable
@Preview(showBackground = true)
private fun CheckCardFieldPreview() {
    CheckCardField("4274 2233") {
        " "
    }
}

@Composable
fun FieldDescription() {
    Text(
        text = "Enter the first 6 to 8 digits of a card number (BIN/IIN)",
        fontSize = 12.sp,
        color = Color.Gray
    )
}

@Composable
@Preview(showBackground = true)
private fun FieldDescriptionPreview() {
    FieldDescription()
}

@Composable
fun LookUpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
    ) {
        Text(
            text = "Lookup",
            color = Color.White,
            fontSize = 16.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LookUpButtonPreview() {
    LookUpButton(){}
}

@Composable
fun CardInfo(cardInfo: CardInfo) {
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
            Text(
                text = "${cardInfo.brand} (${cardInfo.type})",
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = cardInfo.country.emoji, fontSize = 24.sp)
            }
            Text(
                text = cardInfo.country.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = if (cardInfo.prepaid) "Предоплата: Да" else "Предоплата: Нет",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Длина номера карты: ${cardInfo.cardNumber.length}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
            )
            Text(
                text = if (cardInfo.cardNumber.luhn) "Алгоритм Луна: Да" else "Алгоритм Луна: Нет",
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
        Text(text = "Банк: ${cardInfo.bank.name}", fontWeight = FontWeight.Medium)
        Text(text = "Валюта: ${cardInfo.country.currency}", color = Color.Gray)
    }

    Text(text = "Город: ${cardInfo.bank.city}", fontWeight = FontWeight.Medium)

    Text(
        text = "Широта: ${cardInfo.country.latitude} " +
                "Долгота: ${cardInfo.country.longitude}"
    )
    Text(
        text = "Телефон: ${cardInfo.bank.phone}",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Blue,
    )
    Text(
        text = "Сайт: ${cardInfo.bank.url}",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Blue,
    )
}

@Composable
@Preview(showBackground = true)
private fun CardInfoPreview(){
    CardInfo(
        bank = Bank("Hjorring", "Jyske Bank", "+4589893300", "www.jyskebank.dk"),
        brand = "Visa",
        country = Country(
            "DK",
            "DKK",
            "dk",
            56,
            10,
            "Denmark",
            "208"
        ),
        cardNumber = CardNumber(16, true),
        prepaid = false,
        scheme = "visa",
        type = "debit"
    )
}
