package com.assignments.stockmarket.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.assignments.stockmarket.R
import kotlin.math.abs
import kotlin.math.min

@Composable
fun ScrollableCandleChart(
    candles: List<Candle>,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    val candleWidth = 12.dp
    val candleSpacing = 4.dp

    val chartWidth = (candles.size * (candleWidth + candleSpacing))

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
    ) {

        Canvas(
            modifier = Modifier
                .width(chartWidth)
                .height(300.dp)
                .background(colorResource(R.color.screen_background))
        ) {

            val candleWidthPx = candleWidth.toPx()
            val candleSpacingPx = candleSpacing.toPx()

            val maxPrice = candles.maxOf { it.high }
            val minPrice = candles.minOf { it.low }
            val priceRange = maxPrice - minPrice

            fun priceToY(price: Float): Float {
                return size.height - ((price - minPrice) / priceRange) * size.height
            }

            candles.forEachIndexed { index, candle ->

                val x = index * (candleWidthPx + candleSpacingPx) + candleWidthPx / 2

                val openY = priceToY(candle.open)
                val closeY = priceToY(candle.close)
                val highY = priceToY(candle.high)
                val lowY = priceToY(candle.low)

                val color =
                    if (candle.close >= candle.open) Color(0xFF7CFC00)
                    else Color(0xFFFF6B6B)

                // Wick
                drawLine(
                    color = color,
                    start = Offset(x, highY),
                    end = Offset(x, lowY),
                    strokeWidth = 2f
                )

                // Body
                drawRect(
                    color = color,
                    topLeft = Offset(
                        x - candleWidthPx / 2,
                        min(openY, closeY)
                    ),
                    size = Size(
                        candleWidthPx,
                        abs(openY - closeY)
                    )
                )
            }
        }
    }
}