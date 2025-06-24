package com.jamq.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import com.jamq.app.components.JudulBlokDenganJam
import com.jamq.app.components.*

@Composable
fun InformasiScreen(navController: NavHostController) {
    val context = LocalContext.current

    var namaMasjid by remember { mutableStateOf("") }
    var info1 by remember { mutableStateOf("") }
    var info2 by remember { mutableStateOf("") }
    var stlhIqomah by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFFB3E5FC),
                        Color(0xFF01579B)
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
            JudulBlokDenganJam("Informasi")

            GradientOutlinedTextField(
                value = namaMasjid,
                onValueChange = { namaMasjid = it },
                label = "Nama Masjid",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            GradientOutlinedTextField(
                value = info1,
                onValueChange = { info1 = it },
                label = "Info 1",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            GradientOutlinedTextField(
                value = info2,
                onValueChange = { info2 = it },
                label = "Info 2",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            GradientOutlinedTextField(
                value = stlhIqomah,
                onValueChange = { stlhIqomah = it },
                label = "Setelah Iqomah",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            TombolKirim(
                text = "Kirim",
                onClick = {
                    if (namaMasjid.isNotEmpty() && info1.isNotEmpty()) {
                        sendInfoToESP(namaMasjid, info1, info2, stlhIqomah)
                        Toast.makeText(context, "Data info dikirim ke ESP", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Nama Masjid & Info wajib diisi", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            TombolKembali(navController)
        }
    }
}

