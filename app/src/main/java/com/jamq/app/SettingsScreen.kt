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
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import com.jamq.app.components.JudulBlokDenganJam

@Composable
fun SettingScreen(navController: NavHostController) {
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
    var hijriyah by remember { mutableStateOf("") }
    var kecerahan by remember { mutableStateOf("") }
    var volumeAdzan by remember { mutableStateOf("") }
    var volumeJam by remember { mutableStateOf("") }
    var beep by remember { mutableStateOf(false) }
    var tartil by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        JudulBlokDenganJam("Pengaturan Tambahan")
        OutlinedTextField(
            value = hijriyah,
            onValueChange = { hijriyah = it },
            label = { Text("Hijriyah (+/-)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = kecerahan,
            onValueChange = { kecerahan = it },
            label = { Text("Kecerahan (0-255)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = volumeAdzan,
            onValueChange = { volumeAdzan = it },
            label = { Text("Volume Adzan (0-30)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = volumeJam,
            onValueChange = { volumeJam = it },
            label = { Text("Volume Jam (0-30)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Beep")
            Switch(checked = beep, onCheckedChange = { beep = it })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tartil")
            Switch(checked = tartil, onCheckedChange = { tartil = it })
        }

        TombolKirim(
            text = "Kirim",
            onClick = {
                if (
                    hijriyah.isBlank() || kecerahan.isBlank() ||
                    volumeAdzan.isBlank() || volumeJam.isBlank()
                ) {
                    Toast.makeText(context, "Semua kolom angka wajib diisi!", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        sendSettingsToESP(
                            hijriyah.toInt(),
                            kecerahan.toInt(),
                            volumeAdzan.toInt(),
                            volumeJam.toInt(),
                            beep,
                            tartil
                        )
                        Toast.makeText(context, "Pengaturan dikirim ke ESP", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Input tidak valid!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        TombolKembali(navController = navController)
    }
}
