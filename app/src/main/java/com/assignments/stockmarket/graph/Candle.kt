package com.assignments.stockmarket.graph

/**
 * A single Japanese candlestick.
 * [open]  – price at the start of the period
 * [close] – price at the end of the period
 * [high]  – highest price during the period
 * [low]   – lowest price during the period
 * [timestamp] – epoch millis when this candle started (0 for static/hardcoded candles)
 */
data class Candle(
    val open: Float,
    val close: Float,
    val high: Float,
    val low: Float,
    val timestamp: Long = 0L
)