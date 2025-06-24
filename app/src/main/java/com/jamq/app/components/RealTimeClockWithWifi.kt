package com.jamq.app.components

import android.content.Intent
import android.net.wifi.WifiManager
import android.provider.Settings
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.jamq.app.R
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.PermissionStatus


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RealTimeClockWithWifi() {
    val context = LocalContext.current
    var isConnectedToESP by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }

    // Gunakan permissionState dari Accompanist
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Minta permission jika belum granted
    LaunchedEffect(Unit) {
        if (locationPermissionState.status is PermissionStatus.Denied) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    // Loop realtime update waktu dan cek koneksi
    LaunchedEffect(Unit) {
        while (true) {
            val now = Calendar.getInstance().time
            currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(now)
            currentDate = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID")).format(now)

            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val ssidRaw = wifiManager.connectionInfo.ssid
            val ssid = ssidRaw?.removePrefix("\"")?.removeSuffix("\"") ?: ""

            // Cek koneksi ke SSID yang mengandung ESP
            isConnectedToESP = ssid.contains("ESP", ignoreCase = true)

            delay(1000)
        }
    }

    // Gradasi warna teks
    val gradientBrush = Brush.horizontalGradient(
        listOf(Color(0xFFFF9800), Color(0xFFFF0000)) // orange -> merah
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Black, shape = RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kolom jam + tanggal
            Column {
                Text(
                    text = currentTime,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    style = LocalTextStyle.current.copy(brush = gradientBrush)
                )
                Text(
                    text = currentDate,
                    fontSize = 14.sp,
                    style = LocalTextStyle.current.copy(brush = gradientBrush)
                )
            }

            // Ikon WiFi ESP
            val iconRes = if (isConnectedToESP) R.drawable.ic_wifiblue else R.drawable.ic_wifired

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "WiFi Status",
                modifier = Modifier
                    .size(64.dp) // Atau 80.dp untuk lebih besar
                    .clickable {
                        if (!isConnectedToESP) {
                            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                        }
                    }
            )
        }
    }
}

