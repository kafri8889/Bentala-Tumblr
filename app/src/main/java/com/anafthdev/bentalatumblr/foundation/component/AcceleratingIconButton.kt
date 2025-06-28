package com.anafthdev.bentalatumblr.foundation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AcceleratingIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)
) {
    var isPressed by remember { mutableStateOf(false) }
    // Untuk animasi tekan
    val scale by animateFloatAsState(if (isPressed) 0.85f else 1f)

    // LaunchedEffect akan berjalan ketika isPressed berubah
    LaunchedEffect(isPressed) {
        if (isPressed) {
            // Saat tombol ditekan, jalankan onClick sekali
            onClick()

            // Coroutine untuk menahan (hold)
            coroutineScope {
                launch {
                    // 1. Tahan dulu 500ms sebelum pengulangan
                    delay(500L)

                    var currentDelay = 200L // Delay awal
                    while (true) {
                        onClick()
                        delay(currentDelay)
                        // 2. Akselerasi
                        currentDelay = (currentDelay - 25L).coerceAtLeast(50L)
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .size(48.dp)
            .scale(scale) // Terapkan animasi skala
            .clip(CircleShape) // Membuat area klik berbentuk lingkaran
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(), // Matikan ripple effect bawaan jika tidak mau
                onClick = {}
            )
            .pointerInput(Unit) {
                // Mendeteksi event tekan dan lepas secara manual
                awaitPointerEventScope {
                    while (true) {
                        // Tunggu sampai jari menekan layar
                        awaitFirstDown(requireUnconsumed = false)
                        isPressed = true
                        // Tunggu sampai jari diangkat atau gestur dibatalkan
                        waitForUpOrCancellation()
                        isPressed = false
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
