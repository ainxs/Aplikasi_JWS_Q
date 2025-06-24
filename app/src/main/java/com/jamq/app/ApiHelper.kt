package com.jamq.app

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import android.content.Context

// SETTING JAM DAN TANGGAL //
fun sendDateTimeToESP(tanggal: String, waktu: String) {
    val url = "http://192.168.4.1/waktu?isi_tanggal=$tanggal&isi_jam=$waktu"
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ESP32", "Gagal connect ke ESP: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("ESP32", "Sukses update waktu: ${response.body?.string()}")
            } else {
                Log.e("ESP32", "Gagal! Kode: ${response.code}")
            }
        }
    })
}


// SETTING LOKASI //
fun sendLocationToESP(latitude: Double, longitude: Double, altitude: Double) {
    val url = "http://192.168.4.1/simpanLokasi"  // Sesuai endpoint ESP kamu

    val json = JSONObject().apply {
        put("latitude", latitude)
        put("longitude", longitude)
        put("altitude", altitude)
    }

    val client = OkHttpClient()

    val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.e("ESP32", "Gagal kirim lokasi: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            if (response.isSuccessful) {
                Log.d("ESP32", "Berhasil update lokasi: ${response.body?.string()}")
            } else {
                Log.e("ESP32", "Gagal update lokasi! Kode: ${response.code}")
            }
        }
    })
}

fun sendSettingsToESP(
    hijriyah: Int,
    kecerahan: Int,
    volumeAdzan: Int,
    volumeJam: Int,
    beep: Boolean,
    tartil: Boolean
) {
    val url = "http://192.168.4.1/simpanAtur"
    val json = JSONObject().apply {
        put("adjhijr", hijriyah)
        put("adjcerah", kecerahan)
        put("adjvolumeadzan", volumeAdzan)
        put("adjvolume", volumeJam)
        put("beepstatus", if (beep) "1" else "0")
        put("tartil", if (tartil) "1" else "0")
    }

    val body = json.toString().toRequestBody("text/plain".toMediaTypeOrNull())

    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.e("ESP32", "Kirim pengaturan gagal: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            Log.d("ESP32", "Respons: ${response.body?.string()}")
        }
    })
}

fun sendInfoToESP(nama: String, info1: String, info2: String, stlhiqomah: String) {
    val url = "http://192.168.4.1/simpanNamamasjid"

    val json = JSONObject().apply {
        put("namamasjid", nama)
        put("info1", info1)
        put("info2", info2)
        put("stlhiqomah", stlhiqomah)
    }

    val client = OkHttpClient()
    val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())

    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.e("ESP32", "Gagal kirim info: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            Log.d("ESP32", "Berhasil update info: ${response.body?.string()}")
        }
    })
}

fun buildJsonWarna(warnaMap: Map<String, Color>): JSONObject {
    val json = JSONObject()
    warnaMap.forEach { (key, color) ->
        val hex = String.format("#%06X", 0xFFFFFF and color.toArgb())
        json.put(key, hex)
    }
    return json
}

fun sendWarnaToESP(json: JSONObject) {
    val url = "http://192.168.4.1/simpanWarna"
    val client = OkHttpClient()

    val jsonString = json.toString()
    val mediaType = "application/json; charset=utf-8".toMediaType()
    val body = jsonString.toRequestBody(mediaType)

    val request = Request.Builder()
        .url(url)
        .post(body)
        .addHeader("Content-Type", "application/json")
        .addHeader("Content-Length", jsonString.length.toString()) // 🔥 PENTING BANGET
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ESP32", "Gagal kirim warna: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("ESP32", "Berhasil update warna: ${response.body?.string()}")
            } else {
                Log.e("ESP32", "Gagal! Kode: ${response.code}")
            }
        }
    })
}


fun sendKoreksiToESP(data: Map<String, Int>) {
    val url = "http://192.168.4.1/simpanKoreksi"
    val json = JSONObject()

    data.forEach { (key, value) ->
        json.put(key, value)
    }

    val client = OkHttpClient()
    val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())

    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ESP32", "Gagal kirim koreksi: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            Log.d("ESP32", "Koreksi berhasil dikirim: ${response.body?.string()}")
        }
    })
}

object ApiHelper {
    fun sendIqomahToESP(
        lamaAdzan: Int,
        iqSubuh: Int,
        iqDzuhur: Int,
        iqAshar: Int,
        iqMaghrib: Int,
        iqIsya: Int
    ) {
        val url = "http://192.168.4.1/simpanIqomah"
        val json = JSONObject().apply {
            put("lamaadzan", lamaAdzan)
            put("iqomahsubuh", iqSubuh)
            put("iqomahdzuhur", iqDzuhur)
            put("iqomahashar", iqAshar)
            put("iqomahmaghrib", iqMaghrib)
            put("iqomahisya", iqIsya)
        }

        val client = OkHttpClient()
        val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ESP32", "❌ Gagal kirim iqomah: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("ESP32", "✅ Berhasil kirim iqomah: ${response.body?.string()}")
            }
        })
}

fun sendTampilToESP(
    tplNama: Boolean,
    tplPesan: Boolean,
    tplPesan2: Boolean,
    tplHijriyah: Boolean,
    tplMasehi: Boolean,
    tplMatikan: Boolean
) {
    val json = JSONObject().apply {
        put("tpl_nama", tplNama)
        put("tpl_pesan", tplPesan)
        put("tpl_pesan2", tplPesan2)
        put("tpl_hijriyah", tplHijriyah)
        put("tpl_masehi", tplMasehi)
        put("tpl_matikan", tplMatikan)
    }

    val url = "http://192.168.4.1/simpanTampiljws"
    val client = OkHttpClient()

    val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ESP32", "❌ Gagal kirim tampilan: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("ESP32", "✅ Berhasil update tampilan: ${response.body?.string()}")
            } else {
                Log.e("ESP32", "⚠️ Gagal update tampilan! Kode: ${response.code}")
            }
        }
    })
}}

fun kirimPerintahTartil(context: Context, file: String, aksi: String) {
    val url = "http://192.168.4.1/tartil?file=$file&action=$aksi"
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ESP32", "❌ Gagal kirim tartil: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("ESP32", "▶️ Tartil berhasil: ${response.body?.string()}")
            } else {
                Log.e("ESP32", "⚠️ Gagal tartil! Kode: ${response.code}")
            }
        }
    })
}

fun kirimVolume(context: Context, volume: Int) {
    val url = "http://192.168.4.1/volume?val=$volume"
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ESP32", "❌ Gagal set volume: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("ESP32", "🔊 Volume dikirim: ${response.body?.string()}")
            } else {
                Log.e("ESP32", "⚠️ Gagal volume! Kode: ${response.code}")
            }
        }
    })
}
