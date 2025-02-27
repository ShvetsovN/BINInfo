package com.example.bininfo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bininfo.model.Bank
import com.example.bininfo.model.BinInfo
import com.example.bininfo.model.Country
import com.example.bininfo.model.Number
import com.example.bininfo.ui.BinViewModel
import com.example.bininfo.ui.theme.BINInfoTheme
import com.example.bininfo.ui.theme.mainCardColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: BinViewModel = hiltViewModel()
) {
    var bin by remember { mutableStateOf("") }
    val binState by viewModel.binInfo.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("historyScreen") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.List, contentDescription = "History")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = bin,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "BIN/IIN card") },
                onValueChange = { newBin -> bin = formatBinInput(newBin) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            bin = ""
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Иконка очистки поля",
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter the first 6 to 8 digits of a card number (BIN/IIN)",
                fontSize = 16.sp,
                color = Color.Gray
                )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.getBinInfo(bin) },
                enabled = bin.length in 7..9,
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text("LookUp")
            }

            Spacer(modifier = Modifier.height(16.dp))

            binState?.let { item ->
                BinInfo(item)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TextFieldPreview(){
    OutlinedTextField(
        value = "4444 0000",
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "BIN/IIN card") },
        onValueChange = {  },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = { },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Иконка очистки поля",
                )
            }
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun DescriptionPreview(){
    Text(
        text = "Enter the first 6 to 8 digits of a card number (BIN/IIN)",
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
@Preview(showBackground = true)
private fun ButtonPreview(){
    Button(
        onClick = {  },
        colors = ButtonDefaults.buttonColors(Color.Blue)
    ) {
        Text(text = "LookUp", fontSize = 20.sp)
    }
}

private fun formatBinInput(input: String): String {
    return input
        .replace(" ", "")
        .take(8)
        .chunked(4)
        .joinToString(" ")
}

@Composable
fun BinInfo(binInfo: BinInfo) {
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
                Text(text = "${binInfo.brand} (${binInfo.type})", fontWeight = FontWeight.Bold)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = binInfo.country.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = binInfo.country.emoji, fontSize = 24.sp)
            }
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = if (binInfo.prepaid) "Предоплата: Да" else "Предоплата: Нет",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Длина номера карты: ${binInfo.number.length}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                    )
                    Text(
                        text = if (binInfo.number.luhn) "Алгоритм Луна: Да" else "Алгоритм Луна: Нет",
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
                Text(text = "Банк: ${binInfo.bank.name}", fontWeight = FontWeight.Medium)
                Text(text = "Валюта: ${binInfo.country.currency}", color = Color.Gray)
            }

            Text(text = "Город: ${binInfo.bank.city}", fontWeight = FontWeight.Medium)

            Text(
                text = "Широта: ${binInfo.country.latitude} Долгота: ${binInfo.country.longitude}"
            )
            Text(
                text = "Телефон: ${binInfo.bank.phone}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
            )
            Text(
                text = "Сайт: ${binInfo.bank.url}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
            )
        }
    }
}
