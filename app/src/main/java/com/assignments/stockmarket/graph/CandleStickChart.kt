package com.assignments.stockmarket.graph

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import androidx.compose.runtime.LaunchedEffect
import com.assignments.stockmarket.utils.AppTextStyles

private val CANDLE_GREEN = Color(0xFF7CFC00)
private val CANDLE_RED = Color(0xFFFF6B6B)
private val GRID_LINE_COLOR = Color(0xFF1A2D4D)
private val PRICE_LINE_COLOR = Color(0x55FFFFFF)
private val priceFmt = DecimalFormat("#,##0.00")

/**
 * Live animated Japanese candlestick chart with loading state,
 * fluctuating price label and a 1-minute candle timer.
 */
@Composable
fun ScrollableCandleChart(
    candles: List<Candle>,
    formingCandle: Candle? = null,
    animatedPrice: Float = 0f,
    remainingSeconds: Int = 60,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    // ── Loading shimmer state ──
    if (isLoading) {
        CandleChartLoadingShimmer(modifier)
        return
    }

    val scrollState = rememberScrollState()

    val candleWidth = 14.dp
    val candleSpacing = 5.dp

    val totalCandles = candles.size + if (formingCandle != null) 1 else 0
    val chartWidth = max(1, totalCandles) * (candleWidth + candleSpacing) + 40.dp

    // Auto-scroll to the right (latest candle) whenever candles change
    LaunchedEffect(candles.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(modifier = modifier) {
        // ── Timer row ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "1 min candle",
                style = AppTextStyles.bold(14, colorResource(R.color.text_secondary))
            )
            // Countdown timer
            val mins = remainingSeconds / 60
            val secs = remainingSeconds % 60
            Text(
                text = "⏱ ${String.format("%d:%02d", mins, secs)}",
                color = if (remainingSeconds <= 10) CANDLE_RED else Color(0xFFAABBDD),
                style = AppTextStyles.bold(11),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ── Chart ──
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            Canvas(
                modifier = Modifier
                    .width(chartWidth)
                    .height(280.dp)
                    .background(colorResource(R.color.bg_primary))
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

        Spacer(modifier = Modifier.height(6.dp))

        // ── Fluctuating price label at the bottom ──
        if (animatedPrice > 0f) {
            val priceColor = if (formingCandle != null && animatedPrice >= formingCandle.open)
                CANDLE_GREEN else CANDLE_RED

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Animated bobbing arrow
                val infiniteTransition = rememberInfiniteTransition(label = "priceAnim")
                val offsetY by infiniteTransition.animateFloat(
                    initialValue = -2f,
                    targetValue = 2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "bob"
                )
                val arrow = if (formingCandle != null && animatedPrice >= formingCandle.open) "▲" else "▼"

                Text(
                    text = "$arrow ₹${priceFmt.format(animatedPrice)}",
                    style = AppTextStyles.bold(11, priceColor),
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }
    }
}

/**
 * Shimmer / skeleton loading animation for the chart area.
 */
@Composable
private fun CandleChartLoadingShimmer(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF0A1E3D),
            Color(0xFF1A3355),
            Color(0xFF0A1E3D)
        ),
        start = Offset(shimmerOffset - 300f, 0f),
        end = Offset(shimmerOffset, 0f)
    )

    Column(modifier = modifier.fillMaxWidth()) {
        // Fake timer row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Fake candle bars
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            val heights = listOf(120, 80, 150, 60, 180, 100, 140, 90, 160, 70, 130, 110)
            heights.forEach { h ->
                Box(
                    modifier = Modifier
                        .width(12.dp)
                        .height(h.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(shimmerBrush)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        // Fake price label
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .width(90.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(shimmerBrush)
        )
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