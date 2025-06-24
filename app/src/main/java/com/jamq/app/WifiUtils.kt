package com.jamq.app.utils

import android.widget.Toast
import android.content.Context
import android.net.wifi.WifiManager
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import android.net.NetworkRequest
import android.net.NetworkCapabilities
import android.net.wifi.WifiNetworkSpecifier
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.Log

@Composable
fun WifiConnectIcon() {
    val context = LocalContext.current
    var isConnectedToESP by remember { mutableStateOf(false) }
    var previousStatus by remember { mutableStateOf<Boolean?>(null) }

    // Auto-refresh status koneksi setiap 5 detik
    LaunchedEffect(Unit) {
        while (true) {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val ssid = wifiInfo.ssid?.removePrefix("\"")?.removeSuffix("\"") ?: ""
            val nowConnected = ssid.contains("ESP32", ignoreCase = true)

            // Hanya kirim toast kalau status berubah
            if (previousStatus != null && previousStatus != nowConnected) {
                if (nowConnected) {
                    Toast.makeText(context, "✅ Terhubung ke ESP32", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "❌ Belum terhubung ke ESP32", Toast.LENGTH_SHORT).show()
                }
            }

            isConnectedToESP = nowConnected
            previousStatus = nowConnected

            delay(5000)
        }
    }

}

@RequiresApi(Build.VERSION_CODES.Q)
fun connectToESP32(context: Context) {
    val specifier = WifiNetworkSpecifier.Builder()
        .setSsid("JamIOT")
        .setWpa2Passphrase("123456789")
        .build()

    val request = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .setNetworkSpecifier(specifier)
        .build()

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectivityManager.bindProcessToNetwork(network)
            Log.d("WifiConnect", "✅ Berhasil terhubung ke JamIOT")
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.e("WifiConnect", "❌ Gagal terhubung ke JamIOT")
        }
    }

    connectivityManager.requestNetwork(request, callback)
}