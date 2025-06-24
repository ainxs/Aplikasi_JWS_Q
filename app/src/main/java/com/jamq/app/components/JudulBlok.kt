package com.jamq.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun JudulBlok(judul: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Text(
            text = judul,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFF9800), Color.Red)
                )
            )
        )
    }
}

@Composable
fun JudulBlokDenganJam(judul: String) {
    val currentTime by produceState(initialValue = "") {
        while (true) {
            val now = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time)
            value = now
            delay(1000)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = judul,
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFF9800), Color.Red)
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = currentTime,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LabelGradient(text: String) {
    Text(
        text = text,
        style = TextStyle(
            brush = Brush.horizontalGradient(
                listOf(Color(0xFFFF9800), Color.Red)
            ),
            fontWeight = FontWeight.Bold
        )
    )
}


