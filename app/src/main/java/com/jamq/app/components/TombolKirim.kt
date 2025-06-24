package com.jamq.app.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TombolKirim(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.horizontalGradient(
        listOf(Color(0xFFFF9800), Color.Red)
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White // fallback kalau gradient gagal
        )
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        brush = gradient,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(text)
                }
            },
            fontSize = 16.sp
        )
    }
}
