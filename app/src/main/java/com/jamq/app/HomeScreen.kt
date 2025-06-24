package com.jamq.app
import com.jamq.app.utils.WifiConnectIcon
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jamq.app.components.RealTimeClockWithWifi
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background

data class MenuItem(val iconRes: Int, val title: String, val route: String)

@Composable
fun HomeScreen(navController: NavHostController) {
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
    {
        // 🧱 Lapisan isi aplikasi di atas background
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 🔲 Baris atas: Jam + WiFi
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WifiConnectIcon()         // Ikon di kiri
                RealTimeClockWithWifi()   // Jam di kanan
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔳 Menu Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                val menuItems = listOf(
                    MenuItem(R.drawable.ic_jam, "Jam & Tanggal", "datetime"),
                    MenuItem(R.drawable.ic_adzaniqomah, "Adzan Iqomah", "iqomah"),
                    MenuItem(R.drawable.ic_info, "Informasi", "info"),
                    MenuItem(R.drawable.ic_koreksi, "Koreksi", "koreksi"),
                    MenuItem(R.drawable.ic_lokasi, "Lokasi", "lokasi"),
                    MenuItem(R.drawable.ic_tampilan, "Tampilan", "tampilan"),
                    MenuItem(R.drawable.ic_warna, "Warna", "warna"),
                    MenuItem(R.drawable.ic_pengaturan, "Pengaturan", "pengaturan"),
                    MenuItem(R.drawable.ic_tartil, "Tartil", "tartil")
                )

                items(menuItems) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(item.route)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFFF9800), Color.Red) // orange ke merah
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}



