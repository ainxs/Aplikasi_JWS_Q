package com.jamq.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jamq.app.components.JudulBlokDenganJam
import com.jamq.app.components.LabelGradient
import com.jamq.app.components.TombolKembali
import com.jamq.app.kirimPerintahTartil
import com.jamq.app.kirimVolume

@Composable
fun HalamanTartilManual(navController: NavHostController) {
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

    var selectedFile by remember { mutableStateOf("001") }
    var volume by remember { mutableStateOf(20f) }

    val fileList = (1..114).map { num -> "%03d".format(num) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Judul halaman
        JudulBlokDenganJam("TARTIL MANUAL")

        // Dropdown File Tartil
        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = selectedFile,
                onValueChange = {},
                readOnly = true,
                label = { LabelGradient("Pilih File Tartil") },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),    // Warna saat aktif (fokus)
                    unfocusedBorderColor = Color.Red,          // Warna saat tidak aktif
                    disabledBorderColor = Color.Gray,
                    focusedTrailingIconColor = Color.Red       // Warna ikon saat aktif
                )
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                fileList.forEach { file ->
                    DropdownMenuItem(
                        text = { Text("Surat $file") },
                        onClick = {
                            selectedFile = file
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Kontrol
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    kirimPerintahTartil(context, selectedFile, "play")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                LabelGradient("Play")
            }

            Button(
                onClick = {
                    kirimPerintahTartil(context, selectedFile, "pause")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                LabelGradient("Pause")
            }

            Button(
                onClick = {
                    kirimPerintahTartil(context, selectedFile, "stop")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                LabelGradient("Stop")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Slider Volume
        LabelGradient("Volume: ${volume.toInt()}")
        Slider(
            value = volume,
            onValueChange = {
                volume = it
                kirimVolume(context, it.toInt())
            },
            valueRange = 0f..30f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol kembali di bagian bawah
        TombolKembali(navController)
    }
}
