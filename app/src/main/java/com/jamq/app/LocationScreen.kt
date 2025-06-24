package com.jamq.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField // Or androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import com.jamq.app.components.JudulBlokDenganJam
import com.jamq.app.components.*

@Composable
fun LocationScreen(navController: NavHostController) {
    val context = LocalContext.current

    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var altitude by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFFB3E5FC), // Biru muda
                        Color(0xFF01579B)  // Biru tua
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            JudulBlokDenganJam("Lokasi")

            GradientOutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = "Latitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            GradientOutlinedTextField(
                value = longitude,
                onValueChange = { longitude = it },
                label = "Longitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            GradientOutlinedTextField(
                value = altitude,
                onValueChange = { altitude = it },
                label = "Altitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TombolKirim(
                text = "Kirim ke ESP32",
                onClick = {
                    try {
                        val lat = latitude.toDouble()
                        val lon = longitude.toDouble()
                        val alt = altitude.toDouble()
                        sendLocationToESP(lat, lon, alt)
                        Toast.makeText(context, "Data lokasi dikirim", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Input tidak valid!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            TombolKembali(navController)
        }
    }
}
