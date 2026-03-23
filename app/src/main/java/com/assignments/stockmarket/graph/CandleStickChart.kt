package com.assignments.stockmarket.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.assignments.stockmarket.R
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private val CANDLE_GREEN = Color(0xFF7CFC00)
private val CANDLE_RED = Color(0xFFFF6B6B)
private val GRID_LINE_COLOR = Color(0xFF1A2D4D)
private val PRICE_LINE_COLOR = Color(0x55FFFFFF)

/**
 * Live animated Japanese candlestick chart.
 *
 * Features:
 * - Completed candles drawn with body + wick following Japanese rules
 * - Forming candle shown with current price animated via slide
 * - Background grid with horizontal price levels
 * - Auto-scrolls to the latest candle
 * - Green for bullish (close ≥ open), red for bearish
 */
@Composable
fun ScrollableCandleChart(
    candles: List<Candle>,
    formingCandle: Candle? = null,
    animatedPrice: Float = 0f,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val candleWidth = 14.dp
    val candleSpacing = 5.dp

    val totalCandles = candles.size + if (formingCandle != null) 1 else 0
    val chartWidth = max(1, totalCandles) * (candleWidth + candleSpacing) + 40.dp

    // Auto-scroll to the right (latest candle) whenever candles change
    LaunchedEffect(candles.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Row(
        modifier = modifier.horizontalScroll(scrollState)
    ) {
        Canvas(
            modifier = Modifier
                .width(chartWidth)
                .height(280.dp)
                .background(colorResource(R.color.screen_background))
        ) {
            if (totalCandles == 0) return@Canvas

            val candleWidthPx = candleWidth.toPx()
            val candleSpacingPx = candleSpacing.toPx()

            // Build the full list (completed + forming) for price range calculation
            val allCandles = if (formingCandle != null) candles + formingCandle else candles
            // Include animated price in range
            val rawMax = allCandles.maxOf { it.high }
            val rawMin = allCandles.minOf { it.low }
            val maxP = if (animatedPrice > 0f) max(rawMax, animatedPrice) else rawMax
            val minP = if (animatedPrice > 0f) min(rawMin, animatedPrice) else rawMin
            val padding = (maxP - minP) * 0.08f + 0.01f // 8% padding
            val maxPrice = maxP + padding
            val minPrice = minP - padding
            val priceRange = maxPrice - minPrice

            fun priceToY(price: Float): Float {
                return size.height - ((price - minPrice) / priceRange) * size.height
            }

            // ── Background grid lines ──
            drawGridLines(this, minPrice, priceRange, size)

            // ── Draw completed candles ──
            candles.forEachIndexed { index, candle ->
                val centerX = index * (candleWidthPx + candleSpacingPx) + candleWidthPx / 2 + 20.dp.toPx()
                drawJapaneseCandle(this, candle, centerX, candleWidthPx, ::priceToY)
            }

            // ── Draw forming candle (in-progress, uses animated price as close) ──
            if (formingCandle != null) {
                val idx = candles.size
                val centerX = idx * (candleWidthPx + candleSpacingPx) + candleWidthPx / 2 + 20.dp.toPx()

                // Use the animated price as the current close for the forming candle
                val liveClose = if (animatedPrice > 0f) animatedPrice else formingCandle.close
                val liveCandle = formingCandle.copy(
                    close = liveClose,
                    high = max(formingCandle.high, liveClose),
                    low = min(formingCandle.low, liveClose)
                )
                drawJapaneseCandle(this, liveCandle, centerX, candleWidthPx, ::priceToY)

                // Dashed horizontal line at the current price level
                val priceY = priceToY(liveClose)
                drawLine(
                    color = PRICE_LINE_COLOR,
                    start = Offset(0f, priceY),
                    end = Offset(size.width, priceY),
                    strokeWidth = 1f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
                )
            }
        }
    }
}

/**
 * Draws a single Japanese candlestick: body + upper wick + lower wick.
 */
private fun drawJapaneseCandle(
    scope: DrawScope,
    candle: Candle,
    centerX: Float,
    bodyWidth: Float,
    priceToY: (Float) -> Float
) {
    val openY = priceToY(candle.open)
    val closeY = priceToY(candle.close)
    val highY = priceToY(candle.high)
    val lowY = priceToY(candle.low)

    val isBullish = candle.close >= candle.open
    val color = if (isBullish) CANDLE_GREEN else CANDLE_RED

    // Upper wick (from high to top of body)
    val bodyTop = min(openY, closeY)
    val bodyBottom = max(openY, closeY)

    scope.drawLine(
        color = color,
        start = Offset(centerX, highY),
        end = Offset(centerX, bodyTop),
        strokeWidth = 1.5f
    )

    // Lower wick (from bottom of body to low)
    scope.drawLine(
        color = color,
        start = Offset(centerX, bodyBottom),
        end = Offset(centerX, lowY),
        strokeWidth = 1.5f
    )

    // Body
    val bodyHeight = abs(openY - closeY).coerceAtLeast(1.5f) // min 1.5px for doji candles
    scope.drawRect(
        color = color,
        topLeft = Offset(centerX - bodyWidth / 2, bodyTop),
        size = Size(bodyWidth, bodyHeight)
    )
}

/**
 * Draws horizontal grid lines at evenly spaced price levels.
 */
private fun drawGridLines(
    scope: DrawScope,
    minPrice: Float,
    priceRange: Float,
    canvasSize: Size
) {
    val gridCount = 5
    for (i in 0..gridCount) {
        val price = minPrice + priceRange * (i.toFloat() / gridCount)
        val y = canvasSize.height - ((price - minPrice) / priceRange) * canvasSize.height
        scope.drawLine(
            color = GRID_LINE_COLOR,
            start = Offset(0f, y),
            end = Offset(canvasSize.width, y),
            strokeWidth = 0.5f
        )
    }
}