package com.assignments.stockmarket.graph

import com.assignments.stockmarket.MarketTick
import kotlin.math.max
import kotlin.math.min

/**
 * Manages the formation of Japanese candlesticks from live WebSocket tick data.
 *
 * Each tick arrives ~every 3 seconds.  Every candle is exactly **1 minute**
 * (~20 ticks).  The period selector (1M, 6M, 1Y …) represents the *historical
 * time-range* displayed on the chart (1 Month, 6 Months, 1 Year …), NOT the
 * candle duration.
 *
 * Japanese candlestick rules:
 *   - **Open** = first tick price of the period
 *   - **Close** = last tick price of the period
 *   - **High**  = max price in the period
 *   - **Low**   = min price in the period
 *   - If close ≥ open → bullish (green)
 *   - If close < open  → bearish (red)
 *   - New candle opens at the close of the previous one (continuity)
 */
class LiveCandleManager {

    companion object {
        /** Every candle is 1 minute = 20 ticks (tick every ~3 s). */
        const val TICKS_PER_CANDLE = 20

        /** Candle duration in milliseconds (60 seconds). */
        const val CANDLE_DURATION_MS = 60_000L

        /** Max candles kept visible on the chart. */
        const val MAX_CANDLES = 60
    }

    // Completed candles
    private val _candles = mutableListOf<Candle>()
    val candles: List<Candle> get() = _candles.toList()

    // Current candle being formed
    private var currentOpen: Float? = null
    private var currentClose: Float = 0f
    private var currentHigh: Float = Float.MIN_VALUE
    private var currentLow: Float = Float.MAX_VALUE
    private var currentStartTime: Long = 0L
    private var tickCount = 0

    // Latest price for animation target
    var latestPrice: Float = 0f
        private set
    var previousPrice: Float = 0f
        private set

    /**
     * Reset state for a new symbol or new period.
     */
    fun reset(period: String = "1M") {
        _candles.clear()
        currentOpen = null
        currentClose = 0f
        currentHigh = Float.MIN_VALUE
        currentLow = Float.MAX_VALUE
        currentStartTime = 0L
        tickCount = 0
        latestPrice = 0f
        previousPrice = 0f
    }

    /**
     * Returns the remaining seconds for the current forming candle (0..60).
     */
    fun remainingSeconds(): Int {
        if (currentOpen == null) return 60
        val elapsed = System.currentTimeMillis() - currentStartTime
        val remaining = ((CANDLE_DURATION_MS - elapsed) / 1000).coerceIn(0, 60)
        return remaining.toInt()
    }

    /**
     * Feed a new tick into the candle builder.
     * Returns `true` if a new completed candle was added (UI should re-render).
     */
    fun onTick(tick: MarketTick, period: String): Boolean {
        val price = tick.price.toFloat()
        previousPrice = latestPrice
        latestPrice = price

        val now = System.currentTimeMillis()

        if (currentOpen == null) {
            // First tick ever — start building the first candle
            currentOpen = price
            currentHigh = price
            currentLow = price
            currentClose = price
            currentStartTime = now
            tickCount = 1
            return false
        }

        // Update current candle with this tick
        currentClose = price
        currentHigh = max(currentHigh, price)
        currentLow = min(currentLow, price)
        tickCount++

        // Check if candle is complete (time-based OR tick-based)
        val elapsed = now - currentStartTime
        if (tickCount >= TICKS_PER_CANDLE || elapsed >= CANDLE_DURATION_MS) {
            // Finalize the current candle
            val completedCandle = Candle(
                open = currentOpen!!,
                close = currentClose,
                high = currentHigh,
                low = currentLow,
                timestamp = currentStartTime
            )
            _candles.add(completedCandle)

            // Trim to max visible candles
            if (_candles.size > MAX_CANDLES) {
                _candles.removeAt(0)
            }

            // Start new candle — opens at the close of the previous (Japanese candlestick continuity)
            currentOpen = currentClose
            currentHigh = currentClose
            currentLow = currentClose
            currentStartTime = now
            tickCount = 0
            return true
        }

        return false
    }

    /**
     * Get the in-progress (forming) candle, or null if no data yet.
     */
    fun getCurrentForming(): Candle? {
        val open = currentOpen ?: return null
        return Candle(
            open = open,
            close = currentClose,
            high = currentHigh,
            low = currentLow,
            timestamp = currentStartTime
        )
    }
}

