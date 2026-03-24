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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.bottom_navigation.BottomNavBar
import com.assignments.stockmarket.bottom_navigation.BottomNavItem
import com.assignments.stockmarket.bottom_navigation.screens.f_and_q.FAndQScreen
import com.assignments.stockmarket.bottom_navigation.screens.loans.LoansScreen
import com.assignments.stockmarket.bottom_navigation.screens.mutual_funds.MutualFundsScreen
import com.assignments.stockmarket.bottom_navigation.screens.upi.UPIScreen
import com.assignments.stockmarket.kyc_verification.KYCScreen
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.app_bar.MyAppBar
import com.assignments.stockmarket.tabs.explore.ExploreScreen
import com.assignments.stockmarket.tabs.holdings.HoldingsScreen
import com.assignments.stockmarket.tabs.orders.OrdersScreen
import com.assignments.stockmarket.tabs.positions.PositionsScreen
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import kotlinx.coroutines.delay
import android.util.Log
import com.assignments.stockmarket.db.CompanyEntity
import com.assignments.stockmarket.db.CompanyRepository
import java.text.DecimalFormat
import kotlin.math.abs

private val priceFormat = DecimalFormat("#,##0.00")
private val changeFormat = DecimalFormat("0.00")

@Composable
fun DashboardScreen(
    navController: NavController,
) {

    val context = LocalContext.current

    // ── Fetch companies once and cache into Room ──
    var companies by remember { mutableStateOf<List<CompanyEntity>>(emptyList()) }
    var isLoadingCompanies by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        Log.i("DashboardScreen", "=== COMPANIES LOAD START ===")
        isLoadingCompanies = true

        // Step 1: Check Room cache first
        val cached = CompanyRepository.getCompanies(context)
        if (cached.isNotEmpty()) {
            companies = cached
            isLoadingCompanies = false
            Log.i("DashboardScreen", "Loaded ${cached.size} companies from Room cache (no API call)")
            Log.i("DashboardScreen", "=== COMPANIES LOAD END (from Room) === total=${companies.size}")
            return@LaunchedEffect
        }

        // Step 2: Room is empty — fetch from API
        Log.i("DashboardScreen", "Room is empty. Calling API…")
        try {
            val rawResult = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                val tag = "DashboardScreen"
                var connection: java.net.HttpURLConnection? = null
                try {
                    val url = java.net.URL(COMPANIES_API_URL)
                    Log.i(tag, "Opening connection to: $COMPANIES_API_URL")
                    connection = url.openConnection() as java.net.HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 60_000
                    connection.readTimeout = 60_000
                    connection.setRequestProperty("Accept", "application/json")

                    Log.i(tag, "Connecting…")
                    val responseCode = connection.responseCode
                    Log.i(tag, "HTTP Response Code: $responseCode")

                    val stream = if (responseCode in 200..299) {
                        connection.inputStream
                    } else {
                        connection.errorStream
                    }

                    val body = stream?.bufferedReader()?.use { it.readText() } ?: "EMPTY BODY"
                    Log.i(tag, "Response body length: ${body.length}")
                    body
                } catch (e: Exception) {
                    Log.e(tag, "HTTP Exception: ${e.javaClass.simpleName}: ${e.message}")
                    "ERROR: ${e.message}"
                } finally {
                    connection?.disconnect()
                }
            }

            // Step 3: Parse the raw JSON
            if (rawResult.startsWith("ERROR")) {
                Log.e("DashboardScreen", "API call failed: $rawResult")
            } else {
                val json = org.json.JSONObject(rawResult)
                val dataObj = json.optJSONObject("data")
                val companiesArray = dataObj?.optJSONArray("companies")

                if (companiesArray != null && companiesArray.length() > 0) {
                    val parsed = mutableListOf<CompanyEntity>()
                    for (i in 0 until companiesArray.length()) {
                        val c = companiesArray.getJSONObject(i)
                        parsed.add(
                            CompanyEntity(
                                symbol = c.optString("symbol", ""),
                                name = c.optString("name", ""),
                                logo = c.optString("logo", "")
                            )
                        )
                    }

                    // Step 4: Save to Room
                    Log.i("DashboardScreen", "Saving ${parsed.size} companies to Room DB…")
                    companies = CompanyRepository.saveAndLoad(context, parsed)
                    Log.i("DashboardScreen", "Room DB now has ${companies.size} companies ✓")
                }
            }
        } catch (e: Exception) {
            Log.e("DashboardScreen", "FATAL: ${e.javaClass.simpleName}: ${e.message}", e)
        }

        isLoadingCompanies = false
        Log.i("DashboardScreen", "=== COMPANIES LOAD END (from API) === total=${companies.size}")
    }

    // ── WebSocket lifecycle: connect on enter, disconnect on leave ──
    DisposableEffect(Unit) {
        WebSocketManager.connect()
        onDispose {
            WebSocketManager.disconnect()
        }
    }

    // ── Observe live ticks and connection state from WebSocket ──
    val liveTicks by WebSocketManager.ticks.collectAsState()
    val marketTicks by WebSocketManager.marketTicks.collectAsState()
    val connectionState by WebSocketManager.connectionState.collectAsState()

    // Debug: log market ticks when they arrive
    LaunchedEffect(marketTicks.size) {
        Log.i("DashboardScreen", "📈 marketTicks updated: ${marketTicks.size} symbols → ${marketTicks.keys.take(5)}")
    }

    val bottomNavItems = listOf(
        BottomNavItem("Stocks", Icons.Default.ShowChart),
        BottomNavItem("F&O", Icons.Default.TrendingUp),
        BottomNavItem("Mutual Funds", Icons.Default.AccountBalance),
        BottomNavItem("UPI", Icons.Default.Payment),
        BottomNavItem("Loans", Icons.Default.AttachMoney)
    )

    var selectedBottomTab by remember { mutableIntStateOf(0) }
    // Tab state shared between stickyHeader and tab content
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabItems = listOf("Explore", "Holdings", "Positions", "Orders")

    Scaffold(
        topBar = {
            when (selectedBottomTab) {
                0 -> MyAppBar(navController)
                3 -> AppBarBackArrow(navController, title = "Add Money")
                else -> AppBarBackArrow(navController)
            } },
        bottomBar = {
            BottomNavBar(
                items = bottomNavItems,
                selectedIndex = selectedBottomTab,
                onItemSelected = { selectedBottomTab = it }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(innerPadding)
        ) {
            when (selectedBottomTab) {
                0 -> {
                    // ── Stocks tab: collapsing header + sticky tabs ──
                    @OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {

                        // ── Scrollable: Connection Status + Market Cards ──
                        item {
                            Spacer(modifier = Modifier.height(12.dp))

                            // Connection Status Indicator
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
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

                            // Live Market Cards (horizontal scroll)
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

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // ── Sticky: Tab Row (pins to top on scroll) ──
                        stickyHeader {
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
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                softWrap = false
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        // ── Tab Content (scrolls under the sticky tabs) ──
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxHeight()
                            ) {
                                when (selectedTabIndex) {
                                    0 -> ExploreScreen(navController, companies, isLoadingCompanies, marketTicks)
                                    1 -> HoldingsScreen(navController)
                                    2 -> PositionsScreen(navController)
                                    3 -> OrdersScreen(navController)
                                }
                            }
                        }
                    }
                }
                1 -> FAndQScreen(navController)
                2 -> MutualFundsScreen(navController)
                3 -> UPIScreen(navController)
                4 -> KYCScreen(navController)
            }
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






