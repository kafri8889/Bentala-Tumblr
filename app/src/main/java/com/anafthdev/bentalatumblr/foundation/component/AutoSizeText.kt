package com.anafthdev.bentalatumblr.foundation.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp

/**
 * Sebuah composable Text yang secara otomatis menyesuaikan ukuran font agar pas
 * dalam satu baris tanpa terpotong (overflow).
 *
 * @param text Teks yang akan ditampilkan.
 * @param modifier Modifier yang akan diterapkan pada composable Text.
 * @param style Gaya teks awal yang ingin diterapkan (warna, font weight, dll).
 * @param maxFontSize Ukuran font maksimum yang diinginkan. Secara default akan mengambil dari style.
 * @param minFontSize Ukuran font minimum. Teks tidak akan lebih kecil dari ukuran ini.
 */
@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    maxFontSize: TextUnit = style.fontSize.takeIf { it.isSpecified } ?: 16.sp,
    minFontSize: TextUnit = 8.sp,
) {
    // State untuk menyimpan ukuran font yang akan disesuaikan
    var fontSize by remember { mutableStateOf(maxFontSize) }

    // State untuk mengontrol visibilitas, mencegah teks "berkedip" saat ukuran dihitung
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        modifier = modifier
            // Gunakan alpha untuk menyembunyikan teks saat ukurannya sedang dihitung
            .alpha(if (readyToDraw) 1f else 0f),
        style = style.copy(fontSize = fontSize),
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        onTextLayout = { textLayoutResult ->
            // Callback ini dipanggil setelah teks diukur
            if (textLayoutResult.didOverflowWidth) {
                // Jika teks melebihi lebar yang tersedia (overflow)
                if (fontSize > minFontSize) {
                    // Kurangi ukuran font dan biarkan recomposition terjadi
                    fontSize *= 0.95f // Anda bisa sesuaikan faktor pengecilan ini
                } else {
                    // Jika sudah mencapai batas minimum, siap untuk digambar
                    readyToDraw = true
                }
            } else {
                // Jika teks pas, maka siap untuk digambar
                readyToDraw = true
            }
        }
    )
}
