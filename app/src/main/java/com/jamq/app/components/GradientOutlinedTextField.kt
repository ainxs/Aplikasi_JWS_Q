package com.jamq.app.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun GradientOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val borderColorFocused = Color(0xFFFF9800)
    val borderColorUnfocused = Color.Red
    val cursorColor = Color.Red

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFF9800), Color.Red)
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(brush = gradientBrush)) {
                        append(label)
                    }
                }
            )
        },
        readOnly = readOnly,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColorFocused,
            unfocusedBorderColor = borderColorUnfocused,
            focusedLabelColor = borderColorFocused,
            unfocusedLabelColor = borderColorUnfocused,
            cursorColor = cursorColor
        )
    )
}
