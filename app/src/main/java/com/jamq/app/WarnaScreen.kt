package com.jamq.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import androidx.compose.ui.graphics.toArgb
import android.graphics.Color as AndroidColor
import com.jamq.app.components.JudulBlokDenganJam

@Composable
fun WarnaScreen(navController: NavHostController) {
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

    val labelMap = mapOf(
        "col_jam" to "Jam Digital",
        "col_nama" to "Nama Sholat",
        "col_waktu" to "Waktu Sholat",
        "col_info" to "Info Masjid",
        "col_jam_adzan" to "Jam Adzan",
        "col_jam_iqomah" to "Jam Iqomah",
        "col_adzan" to "Tulisan Adzan",
        "col_iqomah" to "Tulisan Iqomah",
        "col_shaf" to "Tulisan Shaf"
    )

    var warnaMap by remember {
        mutableStateOf(
            mutableMapOf(
                "col_jam" to Color(0xFFb3b700),
                "col_nama" to Color(0xFF9d9d9d),
                "col_waktu" to Color(0xFFFF0044),
                "col_info" to Color(0xFF00c5ff),
                "col_jam_adzan" to Color(0xFFb3b700),
                "col_jam_iqomah" to Color(0xFF00b8ff),
                "col_adzan" to Color(0xFFFF0044),
                "col_iqomah" to Color(0xFFb3b700),
                "col_shaf" to Color(0xFFe100ff)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        JudulBlokDenganJam("Pengaturan Warna")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(warnaMap.keys.toList().size) { index ->
                val key = warnaMap.keys.toList()[index]
                val label = labelMap[key] ?: key
                val color = warnaMap[key] ?: Color.White
                var hue by remember { mutableStateOf(color.toHsvHue()) }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = label, style = MaterialTheme.typography.labelLarge)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Slider(
                            value = hue,
                            onValueChange = { newHue ->
                                hue = newHue
                                val updatedMap = warnaMap.toMutableMap()
                                updatedMap[key] = Color.hsv(newHue, 1f, 1f)
                                warnaMap = updatedMap
                            },
                            valueRange = 0f..360f,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color)
                        )
                    }
                }
            }
        }

        TombolKirim(
            text = "Kirim ke ESP32",
            onClick = {
                val warnaJson = buildJsonWarna(warnaMap)
                sendWarnaToESP(warnaJson)
                Toast.makeText(context, "Warna dikirim ke ESP32", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        TombolKembali(navController)
    }
}

fun Color.toHsvHue(): Float {
    val hsv = FloatArray(3)
    AndroidColor.colorToHSV(this.toArgb(), hsv)
    return hsv[0]
}
