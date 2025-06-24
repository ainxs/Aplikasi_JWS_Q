package com.jamq.app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun TombolKembali(navController: NavHostController) {
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black) // Tombol hitam
    ) {
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(Color(0xFFFF9800), Color.Red)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(brush = gradientBrush, fontWeight = FontWeight.Bold)) {
                    append("Kembali")
                }
            },
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}