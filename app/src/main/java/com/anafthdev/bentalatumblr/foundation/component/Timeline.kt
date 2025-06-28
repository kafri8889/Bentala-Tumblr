package com.anafthdev.bentalatumblr.foundation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// -- Bagian 1: Data Class & Enum untuk Konfigurasi --

/**
 * Mendefinisikan posisi sebuah node dalam timeline.
 * Ini penting untuk menentukan apakah garis atas atau bawah harus digambar.
 */
enum class TimelineNodePosition {
    FIRST,
    MIDDLE,
    LAST
}

/**
 * Parameter untuk men-style lingkaran pada node timeline.
 * @param radius Ukuran radius lingkaran.
 * @param color Warna lingkaran.
 * @param stroke Jika null, lingkaran akan diisi (solid). Jika tidak, akan menjadi outline.
 */
data class CircleParameters(
    val radius: Dp,
    val color: Color,
    val stroke: Stroke? = null
)

/**
 * Parameter untuk men-style garis penghubung antar node.
 * @param strokeWidth Ketebalan garis.
 * @param color Warna garis.
 */
data class LineParameters(
    val strokeWidth: Dp,
    val color: Color
)


// -- Bagian 2: Composable Inti `TimelineNode` --

/**
 * Sebuah Composable yang merepresentasikan satu titik (node) dalam timeline.
 * Composable ini menggambar lingkaran dan garis vertikal yang terhubung dengannya.
 *
 * @param position Posisi node dalam timeline (FIRST, MIDDLE, LAST).
 * @param circleParameters Parameter untuk styling lingkaran.
 * @param lineParameters Parameter untuk styling garis penghubung.
 * @param modifier Modifier untuk Row container.
 * @param content Slot untuk menampilkan konten custom di sebelah kanan node.
 */
@Composable
fun TimelineNode(
    position: TimelineNodePosition,
    circleParameters: CircleParameters,
    modifier: Modifier = Modifier,
    lineParameters: LineParameters? = null,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Membuat tinggi Row minimal setinggi konten terbesarnya
    ) {
        // Kolom untuk visual timeline (garis dan lingkaran)
        TimelineNodeIndicator(
            position = position,
            circleParameters = circleParameters,
            lineParameters = lineParameters
        )
        // Konten yang disediakan oleh user
        content()
    }
}

@Composable
private fun TimelineNodeIndicator(
    position: TimelineNodePosition,
    circleParameters: CircleParameters,
    lineParameters: LineParameters?
) {
    val defaultLineParameters = LineParameters(
        strokeWidth = 2.dp,
        color = circleParameters.color
    )

    val currentLineParameters = lineParameters ?: defaultLineParameters

    Canvas(
        modifier = Modifier
            .width(40.dp) // Lebar area gambar
            .fillMaxHeight()
    ) {
        val circleRadiusInPx = circleParameters.radius.toPx()
        val lineStrokeWidthInPx = currentLineParameters.strokeWidth.toPx()

        // Gambar garis di atas lingkaran (jika bukan node pertama)
        if (position != TimelineNodePosition.FIRST) {
            drawTimelineLine(
                lineColor = currentLineParameters.color,
                strokeWidth = lineStrokeWidthInPx,
                start = Offset(x = size.width / 2, y = 0f),
                end = Offset(x = size.width / 2, y = (size.height / 2) - circleRadiusInPx)
            )
        }

        // Gambar garis di bawah lingkaran (jika bukan node terakhir)
        if (position != TimelineNodePosition.LAST) {
            drawTimelineLine(
                lineColor = currentLineParameters.color,
                strokeWidth = lineStrokeWidthInPx,
                start = Offset(x = size.width / 2, y = (size.height / 2) + circleRadiusInPx),
                end = Offset(x = size.width / 2, y = size.height)
            )
        }

        // Gambar lingkaran
        drawCircle(
            color = circleParameters.color,
            radius = circleRadiusInPx,
            center = Offset(x = size.width / 2, y = size.height / 2),
            style = circleParameters.stroke ?: Stroke(width = 0f) // Isi penuh jika stroke null
        )
    }
}

private fun DrawScope.drawTimelineLine(
    lineColor: Color,
    strokeWidth: Float,
    start: Offset,
    end: Offset,
) {
    drawLine(
        color = lineColor,
        start = start,
        end = end,
        strokeWidth = strokeWidth
    )
}

// -- Bagian 3: Contoh Penggunaan --

// Data class untuk merepresentasikan setiap event di timeline
data class TimelineEvent(
    val title: String,
    val subtitle: String,
    val isCurrent: Boolean = false
)

// List data event seperti pada gambar
val timelineEvents = listOf(
    TimelineEvent(
        title = "The shipment has arrived at the terminal.",
        subtitle = "March 23 at 08.20 - 5020 BERGEN",
        isCurrent = true
    ),
    TimelineEvent(
        title = "The shipment is in transit.",
        subtitle = "March 22 at 07.20 - 0024 OSLO"
    ),
    TimelineEvent(
        title = "The broadcast is sorted and forwarded.",
        subtitle = "March 22 at 00.38 - 0024 OSLO"
    ),
    TimelineEvent(
        title = "Consignment has been dispatched to the terminal and forwarded.",
        subtitle = "March 22 at 00.33 - 0024 OSLO"
    ),
    TimelineEvent(
        title = "We have received information about the shipment, which is currently with the sender or is on its way to Bring's terminal. The tracking is updated when the shipment has arrived at the terminal in the receiving country.",
        subtitle = "March 21 at 07.35"
    )
)

@Composable
fun ShipmentTimeline() {
    val density = LocalDensity.current

    val purpleColor = Color(0xFF6A4CEB)
    val grayColor = Color.Gray

    // Hapus `verticalArrangement` dari sini
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp) // Padding horizontal bisa di sini
    ) {
        itemsIndexed(timelineEvents) { index, event ->
            val position = when (index) {
                0 -> TimelineNodePosition.FIRST
                timelineEvents.lastIndex -> TimelineNodePosition.LAST
                else -> TimelineNodePosition.MIDDLE
            }

            val circleParams = if (event.isCurrent) {
                CircleParameters(radius = 8.dp, color = purpleColor)
            } else {
                CircleParameters(radius = 8.dp, color = grayColor, stroke = Stroke(width = with(density) { 2.dp.toPx() }))
            }

            val lineParams = LineParameters(strokeWidth = 2.dp, color = grayColor)

            TimelineNode(
                position = position,
                circleParameters = circleParams,
                lineParameters = lineParams,
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = event.title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = event.subtitle,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShipmentTimelinePreview() {
    Surface(color = MaterialTheme.colorScheme.background) {
        ShipmentTimeline()
    }
}
