package com.jamq.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jamq.app.components.TombolKembali
import com.jamq.app.components.TombolKirim
import com.jamq.app.components.JudulBlokDenganJam

@Composable
fun TampilanScreen(navController: NavHostController) {
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

    var tplNama by remember { mutableStateOf(false) }
    var tplPesan by remember { mutableStateOf(false) }
    var tplPesan2 by remember { mutableStateOf(false) }
    var tplHijriyah by remember { mutableStateOf(false) }
    var tplMasehi by remember { mutableStateOf(false) }
    var tplMatikan by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        JudulBlokDenganJam("Tampilan Informasi")
        TampilanSwitch("Tampilkan Nama Masjid", tplNama) { tplNama = it }
        TampilanSwitch("Tampilkan Info 1", tplPesan) { tplPesan = it }
        TampilanSwitch("Tampilkan Info 2", tplPesan2) { tplPesan2 = it }
        TampilanSwitch("Tampilkan Tanggal Hijriyah", tplHijriyah) { tplHijriyah = it }
        TampilanSwitch("Tampilkan Tanggal Masehi", tplMasehi) { tplMasehi = it }
        TampilanSwitch("Matikan Setelah Iqomah", tplMatikan) { tplMatikan = it }

        TombolKirim(
            text = "Kirim",
            onClick = {
                ApiHelper.sendTampilToESP( // ✅ Perbaikan di sini
                    tplNama, tplPesan, tplPesan2,
                    tplHijriyah, tplMasehi, tplMatikan
                )
                Toast.makeText(context, "Pengaturan dikirim ke ESP32", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        )

        TombolKembali(navController)
    }
}

@Composable
fun TampilanSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
