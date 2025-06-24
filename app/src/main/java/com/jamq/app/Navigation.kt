package com.jamq.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jamq.app.ui.HalamanTartilManual

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("info") {
            InformasiScreen(navController)
        }
        composable("datetime") {
            DateTimeScreen(navController)
        }
        composable("lokasi") {
            LocationScreen(navController)
        }
        composable("pengaturan") {
            SettingScreen(navController)
        }
        composable("warna") {
            WarnaScreen(navController)
        }
        composable("koreksi") {
            KoreksiScreen(navController)
        }
        composable("iqomah") {
            IqomahScreen(navController)
        }
        composable("tampilan") {
            TampilanScreen(navController)
        }
        composable("tartil") {
            HalamanTartilManual(navController)
        }
        // tambahan disini:
        // composable("adzaniqomah") { AdzanIqomahScreen() }
        // composable("lokasi") { LokasiScreen() }
        // dst.
    }
}


