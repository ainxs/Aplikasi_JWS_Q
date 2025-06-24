package com.jamq.app
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jamq.app.components.TombolKembali
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.jamq.app.ApiHelper.sendIqomahToESP
import com.jamq.app.components.TombolKirim
import com.jamq.app.components.JudulBlokDenganJam
import com.jamq.app.components.*

@Composable
fun IqomahScreen(navController: NavHostController) {
    val context = LocalContext.current

    var lamaAdzan by remember { mutableStateOf("1") }
    var iqSubuh by remember { mutableStateOf("1") }
    var iqDzuhur by remember { mutableStateOf("1") }
    var iqAshar by remember { mutableStateOf("1") }
    var iqMaghrib by remember { mutableStateOf("1") }
    var iqIsya by remember { mutableStateOf("1") }

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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            JudulBlokDenganJam("Iqomah dan Adzan")

            GradientOutlinedTextField(
                value = lamaAdzan,
                onValueChange = { lamaAdzan = it },
                label = "Lama Adzan (menit)",
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GradientOutlinedTextField(
                    value = iqSubuh,
                    onValueChange = { iqSubuh = it },
                    label = "Iqomah Subuh",
                    modifier = Modifier.weight(1f)
                )
                GradientOutlinedTextField(
                    value = iqDzuhur,
                    onValueChange = { iqDzuhur = it },
                    label = "Iqomah Dzuhur",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GradientOutlinedTextField(
                    value = iqAshar,
                    onValueChange = { iqAshar = it },
                    label = "Iqomah Ashar",
                    modifier = Modifier.weight(1f)
                )
                GradientOutlinedTextField(
                    value = iqMaghrib,
                    onValueChange = { iqMaghrib = it },
                    label = "Iqomah Maghrib",
                    modifier = Modifier.weight(1f)
                )
            }

            GradientOutlinedTextField(
                value = iqIsya,
                onValueChange = { iqIsya = it },
                label = "Iqomah Isya",
                modifier = Modifier.fillMaxWidth()
            )

            TombolKirim(
                text = "Kirim",
                onClick = {
                    sendIqomahToESP(
                        lamaAdzan.toIntOrNull() ?: 1,
                        iqSubuh.toIntOrNull() ?: 1,
                        iqDzuhur.toIntOrNull() ?: 1,
                        iqAshar.toIntOrNull() ?: 1,
                        iqMaghrib.toIntOrNull() ?: 1,
                        iqIsya.toIntOrNull() ?: 1
                    )
                    Toast.makeText(context, "Data Iqomah dikirim ke ESP", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            )

            TombolKembali(navController)
        }
    }
}