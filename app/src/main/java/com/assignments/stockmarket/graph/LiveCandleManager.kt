package com.assignments.stockmarket.graph

import com.assignments.stockmarket.MarketTick
import kotlin.math.max
import kotlin.math.min

/**
 * Manages the formation of Japanese candlesticks from live WebSocket tick data.
 *
 * Each tick arrives ~every 3 seconds.  Based on the selected time interval
 * the ticks are grouped into candles:
 *
 *   1M  → 1-minute candle   → ~20 ticks per candle  (60s / 3s)
 *   6M  → 6-minute candle   → ~120 ticks per candle
 *   1Y  → treated as 5-min  → ~100 ticks per candle
 *   3Y  → treated as 15-min → ~300 ticks per candle
 *   5Y  → treated as 30-min → ~600 ticks per candle
 *   ALL → treated as 60-min → ~1200 ticks per candle
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
        /** How many ticks form one candle for each time-selector label. */
        fun ticksPerCandle(period: String): Int = when (period) {
            "1M"  -> 20    // 1-minute candle  (20 × 3s = 60s)
            "6M"  -> 120   // 6-minute candle
            "1Y"  -> 100   // 5-minute candle
            "3Y"  -> 300   // 15-minute candle
            "5Y"  -> 600   // 30-minute candle
            "ALL" -> 1200  // 60-minute candle
            else  -> 20
        }

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
    private var tickLimit = 20

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
        tickLimit = ticksPerCandle(period)
        latestPrice = 0f
        previousPrice = 0f
    }

    /**
     * Feed a new tick into the candle builder.
     * Returns `true` if a new completed candle was added (UI should re-render).
     */
    fun onTick(tick: MarketTick, period: String): Boolean {
        val price = tick.price.toFloat()
        previousPrice = latestPrice
        latestPrice = price
        tickLimit = ticksPerCandle(period)

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

        // Check if candle is complete
        if (tickCount >= tickLimit) {
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

