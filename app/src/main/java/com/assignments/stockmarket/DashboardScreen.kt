package com.assignments.stockmarket

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

@Composable
fun DashboardScreen(
    navController: NavController,
) {

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
                .padding(innerPadding)  // Important to apply scaffold's padding
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // Screen content changes depending on bottom nav selection
            when (selectedBottomTab) {
                // 0 -> DashboardScreen(navController)
                1 -> FAndQScreen(navController)
                2 -> MutualFundsScreen(navController)
                3 -> BuyStockScreen(navController)
                4 -> LoansScreen(navController)
            }

            // NIFTY 50 and SENSEX cards
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MarketCard(
                    title = "NIFTY 50",
                    price = "25,616.80",
                    change = "+196.50 (0.77%)",
                    isPositive = true,
                    modifier = Modifier.weight(1f),
                )
                MarketCard(
                    title = "SENSEX",
                    price = "82,783.80",
                    change = "+556.50 (0.63%)",
                    isPositive = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            MarketTabs()

        }
    }

}

@Composable
fun MarketCard(
    title: String,
    price: String,
    change: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier,
) {
    val changeColor = if (isPositive) colorResource(R.color.light_green_text_color)
    else colorResource(R.color.red_text_color)

    Card(
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, colorResource(R.color.white)),
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
                fontSize = 10.sp,
                color = colorResource(R.color.white)
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    price,
                    fontWeight = FontWeight.Medium,
                    fontSize = 8.sp,
                    fontFamily = PoppinsFamily,
                    color = colorResource(id = R.color.white)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    change,
                    fontWeight = FontWeight.Medium,
                    fontSize = 8.sp,
                    fontFamily = PoppinsFamily,
                    color = changeColor
                )
            }
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


