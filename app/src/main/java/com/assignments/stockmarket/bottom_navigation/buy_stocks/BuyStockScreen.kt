package com.assignments.stockmarket.bottom_navigation.buy_stocks

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.MarketTick
import com.assignments.stockmarket.R
import com.assignments.stockmarket.WebSocketManager
import com.assignments.stockmarket.db.CompanyEntity
import com.assignments.stockmarket.db.CompanyRepository
import com.assignments.stockmarket.graph.Candle
import com.assignments.stockmarket.graph.LiveCandleManager
import com.assignments.stockmarket.graph.ScrollableCandleChart
import com.assignments.stockmarket.navigation.Routes.DASHBOARD
import com.assignments.stockmarket.reusables.InvestmentsCard
import com.assignments.stockmarket.reusables.MFImageBar
import com.assignments.stockmarket.reusables.SliderOverlay
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.bottom_bar.BottomBarButtons
import com.assignments.stockmarket.tabs.explore.stockDetailsList
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.roundToInt

private val priceDisplayFormat = DecimalFormat("#,##0.00")
private val changeFmt = DecimalFormat("0.00")

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BuyStockScreen(
    navController: NavController,
    stockId: Int,
    stockName: String = ""
) {
    val context = LocalContext.current

    // ── Decode URL-encoded name (Bajaj+Finance → Bajaj Finance) ──
    val decodedName = remember(stockName) {
        try { URLDecoder.decode(stockName, "UTF-8") } catch (_: Exception) { stockName }
    }

    // ── 1. Resolve company from Room DB ──
    var company by remember { mutableStateOf<CompanyEntity?>(null) }
    LaunchedEffect(decodedName) {
        if (decodedName.isNotEmpty()) {
            company = CompanyRepository.getCompanyByName(context, decodedName)
        }
    }

    // Also try the old static list for fallback data (risk, category, theme, etc.)
    val stock = if (decodedName.isNotEmpty()) {
        stockDetailsList.find { it.name.equals(decodedName, ignoreCase = true) }
    } else {
        stockDetailsList.find { it.id == stockId }
    }

    // ── 2. Observe WebSocket ticks for this symbol ──
    val marketTicks by WebSocketManager.marketTicks.collectAsState()
    val symbolKey = company?.symbol ?: ""
    val currentTick: MarketTick? = marketTicks[symbolKey]

    // ── 3. Time period selector ──
    var selectedPeriod by remember { mutableStateOf("1M") }

    // ── 4. LiveCandleManager — builds Japanese candlesticks from ticks ──
    val candleManager = remember { LiveCandleManager() }
    val completedCandles = remember { mutableStateListOf<Candle>() }
    var formingCandle by remember { mutableStateOf<Candle?>(null) }

    // A counter that increments every time we receive a tick — ensures LaunchedEffect re-fires
    var tickVersion by remember { mutableIntStateOf(0) }

    // ── Chart loading state ──
    var chartLoading by remember { mutableStateOf(true) }

    // ── Candle timer (remaining seconds for the current forming candle) ──
    var remainingSeconds by remember { mutableIntStateOf(60) }

    // Reset candle manager when period or symbol changes
    LaunchedEffect(selectedPeriod, symbolKey) {
        candleManager.reset(selectedPeriod)
        completedCandles.clear()
        formingCandle = null
        chartLoading = true
    }

    // ── Timer ticker — updates every second ──
    LaunchedEffect(Unit) {
        while (true) {
            remainingSeconds = candleManager.remainingSeconds()
            delay(1000L)
        }
    }

    // ── 5. Animated price — "glitchy slide" effect ──
    val animatedPrice = remember { Animatable(0f) }
    var displayPrice by remember { mutableFloatStateOf(0f) }
    var displayPrevPrice by remember { mutableFloatStateOf(0f) }

    // Detect tick changes and feed into candle manager IMMEDIATELY (non-blocking)
    LaunchedEffect(currentTick) {
        if (currentTick == null) return@LaunchedEffect

        // First tick received — chart is no longer loading
        chartLoading = false

        // Increment version to ensure we always process
        tickVersion++

        // Feed tick into candle builder — this is fast, no suspend
        val candleCompleted = candleManager.onTick(currentTick, selectedPeriod)
        if (candleCompleted) {
            completedCandles.clear()
            completedCandles.addAll(candleManager.candles)
        }
        // Always update the forming candle snapshot
        formingCandle = candleManager.getCurrentForming()

        // Update remaining seconds immediately on tick
        remainingSeconds = candleManager.remainingSeconds()

        // Update display prices
        displayPrevPrice = displayPrice
        displayPrice = currentTick.price.toFloat()

        // Launch the animation in a separate coroutine so it doesn't block tick processing
        val targetPrice = currentTick.price.toFloat()
        val startPrice = animatedPrice.value
        val diff = targetPrice - startPrice

        if (abs(diff) > 0.001f) {
            // Cancel any ongoing animation and snap partway, then animate the rest
            animatedPrice.snapTo(startPrice + diff * 0.3f)
            launch {
                animatedPrice.animateTo(
                    targetValue = targetPrice,
                    animationSpec = keyframes {
                        durationMillis = 2000
                        // Slide ~60% of the way
                        (startPrice + diff * 0.6f) at 300 using FastOutSlowInEasing
                        // Pull back slightly
                        (startPrice + diff * 0.5f) at 500 using FastOutSlowInEasing
                        // Push to ~85%
                        (startPrice + diff * 0.85f) at 900 using FastOutSlowInEasing
                        // Small overshoot
                        (startPrice + diff * 1.02f) at 1400 using FastOutSlowInEasing
                        // Settle
                        targetPrice at 2000 using FastOutSlowInEasing
                    }
                )
            }
        } else {
            animatedPrice.snapTo(targetPrice)
        }
    }

    // ── Price change computation ──
    val priceDiff = displayPrice - displayPrevPrice
    val isPositive = priceDiff >= 0
    val pctChange = if (displayPrevPrice != 0f) (priceDiff / displayPrevPrice) * 100f else 0f
    val sign = if (isPositive) "+" else "-"
    val changeColor = if (isPositive) colorResource(R.color.text_success_light)
    else colorResource(R.color.text_error)

    // ── Slider overlay state ──
    var showSlider by remember { mutableStateOf(false) }
    var sliderAction by remember { mutableStateOf("BUY") }
    var sliderValue by remember { mutableFloatStateOf(0f) }

    // ── Confirmed quantity after user hits "Confirm" on slider ──
    var confirmedBuyQty by remember { mutableStateOf<Int?>(null) }
    var confirmedSellQty by remember { mutableStateOf<Int?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { AppBarBackArrow(navController) },
            bottomBar = {
                BottomBarButtons(
                    if (confirmedSellQty != null) "PROCEED" else "SELL",
                    if (confirmedBuyQty != null) "PROCEED" else "BUY",
                    {
                        if (confirmedSellQty != null) {
                            // TODO: proceed with sell order
                        } else {
                            sliderAction = "SELL"
                            sliderValue = 0f
                            showSlider = true
                        }
                    },
                    {
                        if (confirmedBuyQty != null) {
                            // TODO: proceed with buy order
                        } else {
                            sliderAction = "BUY"
                            sliderValue = 0f
                            showSlider = true
                        }
                    },
                    R.drawable.ic_red_dot,
                    R.drawable.ic_green_dot,
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.bg_primary))
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(12.dp))

                // ── Company logo + name header ──
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Company logo from Room DB (loaded via Glide)
                    val logoUrl = company?.logo
                    if (logoUrl != null) {
                        GlideImage(
                            model = logoUrl,
                            contentDescription = company?.name ?: "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    color = colorResource(R.color.text_accent_blue_light),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Column {
                        Text(
                            text = company?.name ?: stock?.name ?: decodedName,
                            style = AppTextStyles.bold(18),
                        )
                        if (company != null) {
                            Text(
                                text = company!!.symbol,
                                style = AppTextStyles.medium(12, colorResource(R.color.text_secondary)),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // ── Icon bar (cart, bookmark, search) ──
                MFImageBar()

                Spacer(modifier = Modifier.height(8.dp))

                // ── Risk / Category / Theme (from static data) ──
                if (stock != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stock.risk,
                            style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                            )
                        Text(
                            text = " • ",
                            style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )
                        Text(
                            text = stock.category,
                            style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )
                        Text(
                            text = " • ",
                            style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )
                        Text(
                            text = stock.theme,
                            style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // ── Live price + change ──
                if (displayPrice > 0f) {
                    Text(
                        text = "₹${priceDisplayFormat.format(displayPrice)}",
                        style = AppTextStyles.bold(26),
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${sign}${changeFmt.format(abs(priceDiff))} (${changeFmt.format(abs(pctChange))}%)",
                        style = AppTextStyles.semiBold(14, changeColor),
                    )
                } else {
                    // Fallback from static data
                    stock?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = it.annualReturn.toString(),
                                style = AppTextStyles.bold(20, colorResource(R.color.text_success_light)),
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = it.annualReturnPeriod,
                                style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                                )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it.dayChange.toString(),
                            style = AppTextStyles.bold(14, colorResource(R.color.text_error_light)),
                            )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ── Live Japanese Candlestick Chart (with loading & timer) ──
                ScrollableCandleChart(
                    candles = completedCandles.toList(),
                    formingCandle = formingCandle,
                    animatedPrice = animatedPrice.value,
                    remainingSeconds = remainingSeconds,
                    isLoading = chartLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ── Time period selector ──
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("1M", "6M", "1Y", "3Y", "5Y", "ALL").forEach { period ->
                        val isSelected = period == selectedPeriod
                        Text(
                            text = period,
                            color = if (isSelected) Color(0xFF7CFC00) else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = PoppinsFamily,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .clickable { selectedPeriod = period }
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── Invested & Total Returns Section ──
                InvestmentsCard(
                    investedValue = stock?.investedAmount?.toString() ?: "—",
                    totalReturns = stock?.totalReturns?.toString() ?: "—",
                    arrowClick = { navController.navigate(DASHBOARD) }
                )

                // ── Confirmed Qty Badge(s) below Invested section ──
                if (confirmedBuyQty != null || confirmedSellQty != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        confirmedBuyQty?.let { qty ->
                            ConfirmedQtyBadge(
                                qty = qty,
                                label = "BUY",
                                badgeColor = Color(0xFF4CAF50),
                                onClear = { confirmedBuyQty = null },
                                onClick = {
                                    sliderAction = "BUY"
                                    sliderValue = qty.toFloat()
                                    showSlider = true
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        confirmedSellQty?.let { qty ->
                            ConfirmedQtyBadge(
                                qty = qty,
                                label = "SELL",
                                badgeColor = Color(0xFFE53935),
                                onClear = { confirmedSellQty = null },
                                onClick = {
                                    sliderAction = "SELL"
                                    sliderValue = qty.toFloat()
                                    showSlider = true
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // ── Slider overlay (shown when Buy/Sell tapped) ──
        SliderOverlay(
            visible = showSlider,
            actionLabel = sliderAction,
            value = sliderValue,
            onValueChange = { sliderValue = it },
            onConfirm = { confirmedValue ->
                val qty = confirmedValue.roundToInt()
                if (qty > 0) {
                    if (sliderAction == "BUY") {
                        confirmedBuyQty = qty
                    } else {
                        confirmedSellQty = qty
                    }
                }
                showSlider = false
            },
            onDismiss = { showSlider = false }
        )
    }
}

/**
 * Circular badge showing the confirmed quantity with a cross (X) to clear it.
 * Green circle for BUY, Red circle for SELL.
 */
@Composable
private fun ConfirmedQtyBadge(
    qty: Int,
    label: String,
    badgeColor: Color,
    onClear: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Label above the badge
        Text(
            text = label,
            color = badgeColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFamily
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Circular badge with qty + cross — tapping the circle reopens the slider
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(badgeColor)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = qty.toString(),
                    style = AppTextStyles.bold(18),
                    )
            }

            // Cross (X) button at top-right of the circle
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = 2.dp, y = (-2).dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333))
                    .clickable { onClear() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}
