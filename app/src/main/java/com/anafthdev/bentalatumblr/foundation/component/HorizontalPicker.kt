package com.anafthdev.bentalatumblr.foundation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Komponen Carousel/Picker horizontal yang menempatkan item terpilih di tengah.
 *
 * @param T Tipe data dari item dalam list.
 * @param items Daftar item yang akan ditampilkan.
 * @param onItemSelected Lambda yang akan dipanggil saat item di tengah berubah.
 * @param modifier Modifier untuk komponen ini.
 * @param itemWidth Lebar setiap item dalam carousel.
 * @param itemContent Composable untuk menampilkan satu item. Menerima item data dan status apakah item tersebut sedang di tengah (isCentered).
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> HorizontalPicker(
    state: HorizontalPickerState,
    items: List<T>,
    onItemSelected: (index: Int, item: T) -> Unit,
    modifier: Modifier = Modifier,
    // HAPUS parameter itemWidth yang tidak relevan
    itemContent: @Composable (item: T, isCentered: Boolean) -> Unit
) {
    val configuration = LocalConfiguration.current
    val viewportWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }

    // Update jumlah item ke state saat list berubah
    LaunchedEffect(items.size) {
        state.updateItemCount(items.size)
    }

    // PERBAIKAN PADA DERIVED STATE OF
    val realTimeCenterIndex by remember {
        derivedStateOf {
            // Jika layout belum siap, jangan lakukan apa-apa
            if (state.lazyListState.layoutInfo.visibleItemsInfo.isEmpty()) {
                state.selectedIndex // Kembalikan nilai terakhir yang diketahui
            } else {
                // Garis tengah virtual kita
                val centerLine = state.lazyListState.layoutInfo.viewportStartOffset + (state.lazyListState.layoutInfo.viewportEndOffset / 2)

                // Cari item yang "garis tengah"-nya paling dekat dengan garis tengah virtual
                val centerItem = state.lazyListState.layoutInfo.visibleItemsInfo.minByOrNull {
                    // Jarak antara pusat item dan garis tengah layar
                    abs((it.offset + it.size / 2) - centerLine)
                }

                centerItem?.index ?: state.selectedIndex
            }
        }
    }

    // LaunchedEffect untuk finalisasi state (sudah benar)
    LaunchedEffect(realTimeCenterIndex) {
        if (realTimeCenterIndex != -1 && realTimeCenterIndex != state.selectedIndex) {
            state.updateSelectedIndex(realTimeCenterIndex)
            if (realTimeCenterIndex < items.size) {
                onItemSelected(realTimeCenterIndex, items[realTimeCenterIndex])
            }
        }
    }

    LazyRow(
        state = state.lazyListState,
        // PERBAIKAN PADDING: Kita tidak bisa menggunakan padding dinamis yang fix,
        // karena itu mengacaukan kalkulasi. Fling behavior akan menangani centering.
        // Padding hanya untuk memastikan ada ruang di awal dan akhir.
        contentPadding = PaddingValues(horizontal = (configuration.screenWidthDp / 2).dp),
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state.lazyListState),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(items) { index, item ->
            // HAPUS Box dengan width tetap. Biarkan item punya lebarnya sendiri.
            // Langsung gunakan onSizeChanged pada container item.
            Box(
                modifier = Modifier
                    .onSizeChanged { size ->
                        // Laporkan lebar item yang sebenarnya ke state
                        state.knownWidths[index] = size.width
                    }
            ) {
                itemContent(
                    item,
                    index == realTimeCenterIndex
                )
            }
        }
    }
}

@Stable
class HorizontalPickerState(
    initialIndex: Int,
    private val coroutineScope: CoroutineScope
) {
    internal val lazyListState = LazyListState(firstVisibleItemIndex = initialIndex)
    private var _itemCount by mutableIntStateOf(0)
    internal val knownWidths = mutableStateMapOf<Int, Int>()

    var selectedIndex by mutableIntStateOf(initialIndex)
        private set

    fun next() {
        if (selectedIndex < _itemCount - 1) {
            scrollToItem(selectedIndex + 1)
        }
    }

    fun previous() {
        if (selectedIndex > 0) {
            scrollToItem(selectedIndex - 1)
        }
    }

    fun scrollToItem(index: Int) {
        // Guard clause untuk memastikan list tidak kosong dan index valid
        if (index !in 0 until _itemCount || lazyListState.layoutInfo.visibleItemsInfo.isEmpty()) {
            return
        }

        coroutineScope.launch {
            val viewportWidth = lazyListState.layoutInfo.viewportSize.width
            val targetItemWidth = knownWidths[index]

            // Hanya hitung offset jika lebar item sudah terekam di 'knownWidths'
            val offset = if (targetItemWidth != null) {
                (viewportWidth / 2) - (targetItemWidth / 2)
            } else {
                // Jika tidak tahu lebarnya (item sangat jauh), scroll tanpa offset.
                // Ini akan diperbaiki otomatis oleh snap behavior saat mendekat.
                0
            }

            lazyListState.animateScrollToItem(index, -offset)
        }
    }

    // Fungsi internal untuk diupdate dari Composable
    internal fun updateItemCount(count: Int) {
        _itemCount = count
    }

    internal fun updateSelectedIndex(index: Int) {
        if (index != selectedIndex) {
            selectedIndex = index
        }
    }
}

/**
 * Creates and remembers a [HorizontalPickerState].
 * @param initialIndex Index awal yang akan dipilih.
 */
@Composable
fun rememberHorizontalPickerState(
    initialIndex: Int = 0
): HorizontalPickerState {
    val coroutineScope = rememberCoroutineScope()
    return remember(initialIndex) {
        HorizontalPickerState(initialIndex, coroutineScope)
    }
}
