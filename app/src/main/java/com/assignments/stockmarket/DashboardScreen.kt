package com.assignments.stockmarket

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.bottom_navigation.BottomNavBar
import com.assignments.stockmarket.bottom_navigation.BottomNavItem
import com.assignments.stockmarket.bottom_navigation.screens.BuyStockScreen
import com.assignments.stockmarket.bottom_navigation.screens.FAndQScreen
import com.assignments.stockmarket.bottom_navigation.screens.LoansScreen
import com.assignments.stockmarket.bottom_navigation.screens.MutualFundsScreen
import com.assignments.stockmarket.bottom_navigation.screens.UPIScreen
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.app_bar.MyAppBar
import com.assignments.stockmarket.tabs.explore.ExploreScreen
import com.assignments.stockmarket.tabs.holdings.HoldingsScreen
import com.assignments.stockmarket.tabs.orders.OrdersScreen
import com.assignments.stockmarket.tabs.positions.PositionsScreen
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import kotlin.math.abs

private val priceFormat = DecimalFormat("#,##0.00")
private val changeFormat = DecimalFormat("0.00")

@Composable
fun DashboardScreen(
    navController: NavController,
) {

    // ── WebSocket lifecycle: connect on enter, disconnect on leave ──
    DisposableEffect(Unit) {
        WebSocketManager.connect()
        onDispose {
            WebSocketManager.disconnect()
        }
    }

    // ── Observe live ticks and connection state from WebSocket ──
    val liveTicks by WebSocketManager.ticks.collectAsState()
    val connectionState by WebSocketManager.connectionState.collectAsState()

    val bottomNavItems = listOf(
        BottomNavItem("Stocks", Icons.Default.ShowChart),
        BottomNavItem("F&O", Icons.Default.TrendingUp),
        BottomNavItem("Mutual Funds", Icons.Default.AccountBalance),
        BottomNavItem("UPI", Icons.Default.Payment),
        BottomNavItem("Loans", Icons.Default.AttachMoney)
    )

    var selectedBottomTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {   if (selectedBottomTab == 0) {
            MyAppBar(navController)
        } else {
            AppBarBackArrow(navController)
        } },
        bottomBar = {
            BottomNavBar(
                items = bottomNavItems,
                selectedIndex = selectedBottomTab,
                onItemSelected = { selectedBottomTab = it }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // Screen content changes depending on bottom nav selection
            when (selectedBottomTab) {
                1 -> FAndQScreen(navController)
                2 -> MutualFundsScreen(navController)
                3 -> UPIScreen(navController)
                4 -> LoansScreen(navController)
            }

            // ── Connection Status Indicator ──
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                // Green dot only when LIVE server data, red dot when CONNECTING, no dot for FALLBACK
                when (connectionState) {
                    TickConnectionState.LIVE -> {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF01FF41))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Live",
                            color = Color(0xFF01FF41),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TickConnectionState.FALLBACK -> {
                        Text(
                            text = "Live",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TickConnectionState.CONNECTING -> {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF0105))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Connecting…",
                            color = Color(0xFFFF0105),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // ── Live Market Cards (horizontal scroll) ──
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                LiveMarketCard(
                    title = "NIFTY 50",
                    symbolKey = "NIFTY50",
                    defaultPrice = 25616.80,
                    liveTicks = liveTicks
                )
                LiveMarketCard(
                    title = "SENSEX",
                    symbolKey = "SENSEX",
                    defaultPrice = 82783.80,
                    liveTicks = liveTicks
                )
                LiveMarketCard(
                    title = "BANK NIFTY",
                    symbolKey = "BANKNIFTY",
                    defaultPrice = 48500.00,
                    liveTicks = liveTicks
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            MarketTabs()

        }
    }

}

/**
 * A MarketCard that auto-updates from live WebSocket ticks.
 * Falls back to [defaultPrice] when no tick has arrived yet.
 * Flashes a subtle border color when the price updates.
 */
@Composable
fun LiveMarketCard(
    title: String,
    symbolKey: String,
    defaultPrice: Double,
    liveTicks: Map<String, MarketTick>,
    modifier: Modifier = Modifier
) {
    val tick = liveTicks[symbolKey]

    val currentPrice = tick?.price ?: defaultPrice
    val previousPrice = tick?.previousPrice ?: defaultPrice
    val change = currentPrice - previousPrice
    val isPositive = change >= 0
    val percentChange = if (previousPrice != 0.0) (change / previousPrice) * 100.0 else 0.0

    val sign = if (isPositive) "+" else "-"
    val changeText = "$sign${changeFormat.format(abs(change))} (${changeFormat.format(abs(percentChange))}%)"

    // Flash highlight when a new tick arrives
    var isFlashing by remember { mutableStateOf(false) }

    LaunchedEffect(tick) {
        if (tick != null) {
            isFlashing = true
            delay(600)
            isFlashing = false
        }
    }

    val flashColor = if (isFlashing) {
        if (isPositive) Color(0xFF01FF41).copy(alpha = 0.5f) else Color(0xFFFF0105).copy(alpha = 0.5f)
    } else {
        Color.White
    }
    val animatedBorderColor by animateColorAsState(
        targetValue = flashColor,
        animationSpec = tween(durationMillis = 400),
        label = "borderFlash"
    )

    MarketCard(
        title = title,
        price = priceFormat.format(currentPrice),
        change = changeText,
        isPositive = isPositive,
        borderColor = animatedBorderColor,
        modifier = modifier.width(170.dp)
    )
}

@Composable
fun MarketCard(
    title: String,
    price: String,
    change: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
) {
    val changeColor = if (isPositive) colorResource(R.color.light_green_text_color)
    else colorResource(R.color.red_text_color)

    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.screen_background)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily,
                fontSize = 11.sp,
                color = colorResource(R.color.white)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                price,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                fontFamily = PoppinsFamily,
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                change,
                fontWeight = FontWeight.Medium,
                fontSize = 9.sp,
                fontFamily = PoppinsFamily,
                color = changeColor
            )
        }
    }
}

@Composable
fun MarketTabs() {

    val tabItems = listOf("Explore", "Holdings", "Positions", "Orders")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = colorResource(id = R.color.screen_background),
            contentColor = colorResource(R.color.white),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = colorResource(R.color.white),
                )
            }
        ) {

            tabItems.forEachIndexed { index, title ->

                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    selectedContentColor = colorResource(R.color.white),
                    unselectedContentColor = colorResource(R.color.white).copy(alpha = 0.6f),
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> ExploreScreen()
            1 -> HoldingsScreen()
            2 -> PositionsScreen()
            3 -> OrdersScreen()
        }
    }
}


