package com.jamq.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jamq.app.components.JudulBlok
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import com.jamq.app.components.JudulBlokDenganJam

@Composable
fun KoreksiScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB3E5FC), // Biru muda
                        Color(0xFF01579B)  // Biru tua
                    )
                )
            )
    )

    val context = LocalContext.current

    // State nilai input koreksi
    val koreksiKeys = listOf(
        "korimsak", "korsubuh", "korterbit", "kordhuha",
        "kordzuhur", "korashar", "kormaghrib", "korisya"
    )

    val koreksiLabels = listOf(
        "Imsak", "Subuh", "Terbit", "Dhuha",
        "Dzuhur", "Ashar", "Maghrib", "Isya"
    )

    val koreksiMap = remember {
        koreksiKeys.associateWith { mutableStateOf("2") }.toMutableMap()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
    ) {
        JudulBlokDenganJam("Koreksi Waktu Sholat")
        Spacer(modifier = Modifier.height(10.dp))

        koreksiKeys.forEachIndexed { index, key ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = koreksiLabels[index],
                    modifier = Modifier.weight(1f)
                )

                TextField(
                    value = koreksiMap[key]?.value ?: "",
                    onValueChange = {
                        if (it.all { c -> c.isDigit() } && it.length <= 1) {
                            koreksiMap[key]?.value = it
                        }
                    },
                    modifier = Modifier
                        .width(80.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TombolKirim(
            text = "Kirim ke ESP32",
            onClick = {
                val result = koreksiMap.mapValues { (_, v) -> v.value.toIntOrNull() ?: 0 }
                sendKoreksiToESP(result)
                Toast.makeText(context, "Data koreksi dikirim ke ESP32", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        TombolKembali(navController)
    }
}
