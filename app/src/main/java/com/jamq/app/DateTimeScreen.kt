package com.jamq.app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.ui.text.font.FontWeight
import java.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.background
import com.jamq.app.components.JudulBlokDenganJam

@Composable
fun DateTimeScreen(navController: NavHostController) {
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
    val calendar = remember { Calendar.getInstance() }
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFF9800), Color.Red)
    )
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        JudulBlokDenganJam("Pengaturan Jam & Tanggal")
        // Label tanggal & jam
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF9800), Color.Red)
                    ), fontWeight = FontWeight.Bold)) {
                        append("Tanggal : ")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(selectedDate)
                    }
                },
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF9800), Color.Red)
                    ), fontWeight = FontWeight.Bold)) {
                        append("Waktu : ")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(selectedTime)
                    }
                },
                fontSize = 18.sp
            )
        }


        // Tombol tanggal & jam dalam 1 baris
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TombolKirim(
                text = "Atur Tanggal",
                modifier = Modifier.weight(1f),
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            selectedDate = "%04d-%02d-%02d".format(year, month + 1, day)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            )

            TombolKirim(
                text = "Atur Jam",
                modifier = Modifier.weight(1f),
                onClick = {
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            selectedTime = "%02d:%02d".format(hour, minute)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TombolKirim(
            text = "Kirim ke ESP32",
            onClick = {
                if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                    sendDateTimeToESP(selectedDate, selectedTime)
                    Toast.makeText(context, "Tanggal & Waktu dikirim!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Lengkapi tanggal & waktu dulu ya!", Toast.LENGTH_SHORT).show()
                }
            }
        )


        TombolKembali(navController)
    }
}


