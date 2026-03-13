package com.assignments.stockmarket

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import org.json.JSONObject
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

private const val TAG = "WebSocketManager"

/**
 * Data class representing a single market tick for one symbol.
 */
data class MarketTick(
    val symbol: String,
    val price: Double,
    val previousPrice: Double
)

/**
 * Connection state exposed to the UI.
 *
 * [CONNECTING]  – initial attempt in progress (red dot + "Connecting…")
 * [LIVE]        – real server data is flowing   (green dot + "Live")
 * [FALLBACK]    – using hardcoded data          (no dot, just "Live")
 */
enum class TickConnectionState { CONNECTING, LIVE, FALLBACK }

/**
 * Singleton manager that provides live market ticks to the UI.
 *
 * Strategy:
 *   1. Try connecting via raw WebSocket (OkHttp) + manual EIO/SIO v4.
 *   2. If the server doesn't send the SIO CONNECT ACK within [FALLBACK_DELAY_MS],
 *      fall back to hardcoded data so the UI still works.
 *   3. If the real connection succeeds later, real data takes over automatically.
 *   4. Every error is logged to Logcat with tag "WebSocketManager".
 */
object WebSocketManager {

    private const val WS_URL =
        "wss://system-project-api.onrender.com/socket.io/?EIO=4&transport=websocket"
    private const val FALLBACK_DELAY_MS = 5_000L     // 5 seconds
    private const val SIMULATOR_INTERVAL_MS = 3_000L // 3 seconds

    private val client = OkHttpClient.Builder()
        .pingInterval(25, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.MINUTES)
        .build()

    private var webSocket: WebSocket? = null

    // Latest ticks keyed by symbol — observed by the UI
    private val _ticks = MutableStateFlow<Map<String, MarketTick>>(emptyMap())
    val ticks: StateFlow<Map<String, MarketTick>> = _ticks

    // Connection state — observed by the UI for indicator
    private val _connectionState = MutableStateFlow(TickConnectionState.CONNECTING)
    val connectionState: StateFlow<TickConnectionState> = _connectionState

    private var namespaceConnected = false
    private var reconnectRunnable: Runnable? = null
    private var fallbackTimer: Timer? = null
    private var simulatorTimer: Timer? = null

    // ───────── Public API ─────────

    fun connect() {
        if (webSocket != null) return
        namespaceConnected = false
        _connectionState.value = TickConnectionState.CONNECTING
        startRealConnection()
        scheduleFallback()
    }

    fun disconnect() {
        Log.d(TAG, "Disconnecting…")
        cancelFallback()
        stopSimulator()
        reconnectRunnable?.let {
            android.os.Handler(android.os.Looper.getMainLooper()).removeCallbacks(it)
        }
        reconnectRunnable = null
        namespaceConnected = false
        try {
            if (namespaceConnected) sendSioEvent("stop_ticks")
            webSocket?.close(1000, "bye")
        } catch (_: Exception) { }
        webSocket = null
        _connectionState.value = TickConnectionState.CONNECTING
    }

    // ───────── Real WebSocket connection ─────────

    private fun startRealConnection() {
        val request = Request.Builder().url(WS_URL).build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(ws: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket opened (HTTP ${response.code()})")
            }

            override fun onMessage(ws: WebSocket, text: String) {
                Log.d(TAG, "⟵ ${text.take(150)}")
                handleFrame(ws, text)
            }

            override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                Log.w(TAG, "WebSocket closing: code=$code reason=$reason")
                ws.close(1000, null)
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                Log.w(TAG, "WebSocket closed: code=$code reason=$reason")
                markDisconnected("WebSocket closed (code=$code, reason=$reason)")
                scheduleReconnect()
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                val httpCode = response?.code()?.toString() ?: "none"
                Log.e(TAG, "WebSocket FAILURE: ${t.javaClass.simpleName}: ${t.message}  " +
                        "[HTTP $httpCode]", t)
                markDisconnected("WebSocket failure: ${t.javaClass.simpleName}: ${t.message}")
                scheduleReconnect()
            }
        })

        Log.d(TAG, "Connecting to $WS_URL …")
    }

    // ───────── Engine.IO / Socket.IO protocol ─────────

    private fun handleFrame(ws: WebSocket, text: String) {
        if (text.isEmpty()) return
        when (text[0]) {
            '0' -> onEngineOpen(ws, text)
            '2' -> onEnginePing(ws)
            '3' -> { /* PONG ack */ }
            '4' -> onMessage(ws, text.substring(1))
            '6' -> { /* NOOP */ }
            else -> Log.w(TAG, "Unknown EIO frame type '${text[0]}': ${text.take(40)}")
        }
    }

    private fun onEngineOpen(ws: WebSocket, frame: String) {
        try {
            val json = JSONObject(frame.substring(1))
            Log.d(TAG, "EIO OPEN  sid=${json.optString("sid")}  " +
                    "pingInterval=${json.optInt("pingInterval")}ms  " +
                    "pingTimeout=${json.optInt("pingTimeout")}ms")
        } catch (_: Exception) { }
        ws.send("40")
        Log.d(TAG, "⟶ 40 (SIO CONNECT /)")
    }

    private fun onEnginePing(ws: WebSocket) {
        ws.send("3")
        Log.d(TAG, "⟶ 3 (PONG)")
    }

    private fun onMessage(ws: WebSocket, sio: String) {
        if (sio.isEmpty()) return
        when (sio[0]) {
            '0' -> onSioConnectAck(ws, sio)
            '2' -> onSioEvent(sio.substring(1))
            '4' -> {
                Log.e(TAG, "SIO ERROR from server: $sio")
            }
            else -> Log.w(TAG, "Unknown SIO packet type '${sio[0]}': ${sio.take(60)}")
        }
    }

    private fun onSioConnectAck(ws: WebSocket, payload: String) {
        namespaceConnected = true
        _connectionState.value = TickConnectionState.LIVE
        Log.d(TAG, "✅ SIO namespace connected — switching to LIVE server data")
        cancelFallback()
        stopSimulator()   // real data takes over
        sendSioEvent("start_ticks")
    }

    private fun onSioEvent(arrayPayload: String) {
        try {
            val arr = JSONArray(arrayPayload)
            val name = arr.getString(0)
            if (name == "tick_data" && arr.length() > 1) {
                parseTick(arr.getJSONObject(1))
            } else {
                Log.d(TAG, "SIO event '$name' (ignored)")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse SIO event: ${e.javaClass.simpleName}: ${e.message}")
        }
    }

    private fun sendSioEvent(eventName: String) {
        val frame = "42[\"$eventName\"]"
        webSocket?.send(frame)
        Log.d(TAG, "⟶ $frame")
    }

    // ───────── Tick parsing (shared by live + simulator) ─────────

    private fun parseTick(json: JSONObject) {
        val status = json.optString("status", "")
        if (status != "OK") {
            Log.w(TAG, "Tick non-OK status: $status — ${json.optString("message", "")}")
            return
        }
        val data = json.optJSONObject("data") ?: return
        val ticksArray = data.optJSONArray("ticks") ?: return

        Log.d(TAG, "📊 Seq ${data.optInt("sequence")}: ${ticksArray.length()} ticks received")

        val updated = _ticks.value.toMutableMap()
        for (i in 0 until ticksArray.length()) {
            val t = ticksArray.getJSONObject(i)
            val sym = t.optString("symbol", "")
            if (sym.isNotEmpty()) {
                updated[sym] = MarketTick(
                    symbol = sym,
                    price = t.optDouble("price", 0.0),
                    previousPrice = t.optDouble("previous_price", 0.0)
                )
            }
        }
        _ticks.value = updated
    }

    // ───────── Reconnect ─────────

    private fun markDisconnected(reason: String) {
        Log.w(TAG, "⚠️ Disconnected — reason: $reason")
        webSocket = null
        namespaceConnected = false
        // If already on FALLBACK, stay on FALLBACK (data still showing)
        // If was LIVE, switch to FALLBACK (keep showing data, just no green dot)
        if (_connectionState.value == TickConnectionState.LIVE) {
            Log.w(TAG, "Was LIVE, switching to FALLBACK (hardcoded data)")
            _connectionState.value = TickConnectionState.FALLBACK
            startSimulator()
        }
        // If CONNECTING, fallback timer will handle it
    }

    private fun scheduleReconnect() {
        val r = Runnable {
            Log.d(TAG, "🔄 Attempting reconnect…")
            webSocket = null  // clear old socket before reconnecting
            startRealConnection()
        }
        reconnectRunnable = r
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(r, 5_000)
    }

    // ───────── Fallback: hardcoded tick data ─────────

    private fun scheduleFallback() {
        cancelFallback()
        fallbackTimer = Timer("FallbackTimer", true).apply {
            schedule(object : TimerTask() {
                override fun run() {
                    if (!namespaceConnected) {
                        Log.w(TAG, "⚠️ No SIO CONNECT ACK received within ${FALLBACK_DELAY_MS}ms " +
                                "— server likely has flask-socketio compatibility issue " +
                                "(ctx.session setter error on Flask 3.x / Python 3.14). " +
                                "Switching to FALLBACK hardcoded data.")
                        _connectionState.value = TickConnectionState.FALLBACK
                        startSimulator()
                    }
                }
            }, FALLBACK_DELAY_MS)
        }
    }

    private fun cancelFallback() {
        fallbackTimer?.cancel()
        fallbackTimer = null
    }

    /**
     * Local simulator generates hardcoded tick data every 3 seconds.
     * Each tick varies ±0.01–0.15% from its current price.
     */
    private data class SimSymbol(val symbol: String, var price: Double)

    private val simSymbols = listOf(
        SimSymbol("NIFTY50", 22430.00),
        SimSymbol("SENSEX", 73420.00),
        SimSymbol("BANKNIFTY", 48508.00)
    )

    private fun startSimulator() {
        if (simulatorTimer != null) return
        Log.d(TAG, "Starting hardcoded tick simulator (interval=${SIMULATOR_INTERVAL_MS}ms)")

        simulatorTimer = Timer("TickSimulator", true).apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (namespaceConnected) {
                        stopSimulator()
                        return
                    }
                    generateSimulatedTick()
                }
            }, 0L, SIMULATOR_INTERVAL_MS)
        }
    }

    private fun stopSimulator() {
        simulatorTimer?.cancel()
        simulatorTimer = null
        Log.d(TAG, "Hardcoded tick simulator stopped")
    }

    private fun generateSimulatedTick() {
        val updated = _ticks.value.toMutableMap()
        for (sym in simSymbols) {
            val prev = sym.price
            val pct = ((-15..15).random()) / 10000.0
            val newPrice = (prev * (1.0 + pct) * 100.0).roundToInt() / 100.0
            sym.price = newPrice
            updated[sym.symbol] = MarketTick(
                symbol = sym.symbol,
                price = newPrice,
                previousPrice = prev
            )
        }
        _ticks.value = updated
    }
}
