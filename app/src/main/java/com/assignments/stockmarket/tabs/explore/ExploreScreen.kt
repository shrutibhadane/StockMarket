package com.assignments.stockmarket.tabs.explore

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.MarketTick
import com.assignments.stockmarket.db.CompanyEntity
import com.assignments.stockmarket.navigation.Routes.ALL_STOCKS
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import java.net.URLEncoder

@Composable
fun ExploreScreen(
    navController: NavController,
    companies: List<CompanyEntity> = emptyList(),
    isLoadingCompanies: Boolean = false,
    marketTicks: Map<String, MarketTick> = emptyMap()
) {

    // Filter companies that have live tick data (hide cards with no values)
    val companiesWithTicks = companies.filter { marketTicks.containsKey(it.symbol) }

    // Determine if we're waiting for live data (companies loaded but ticks not yet)
    val isWaitingForTicks = !isLoadingCompanies && companies.isNotEmpty() && companiesWithTicks.isEmpty()

    // Pulsing alpha animation for the "waiting for live data" state
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // ── Full-screen loading when companies are being fetched or waiting for ticks ──
    if (isLoadingCompanies || isWaitingForTicks) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(R.color.white),
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isLoadingCompanies) stringResource(R.string.loading_stocks) else stringResource(
                        R.string.connecting_to_live_data
                    ),
                    style = AppTextStyles.medium(13),
                    modifier = Modifier.alpha(pulseAlpha)
                )
            }
        }
        return
    }

    // ── No data at all → nothing to show ──
    if (companiesWithTicks.isEmpty()) {
        return
    }

    // ── Main content: data is ready ──
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(4.dp))

        // ── Recently Viewed Section ──
        Text(
            text = "Recently Viewed",
            style = AppTextStyles.bold(15),
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Max 8 companies in Recently Viewed
        val recentCompanies = companiesWithTicks.take(8)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(recentCompanies) { company ->
                CompanyRecentItem(
                    company = company,
                    tick = marketTicks[company.symbol],
                    onClick = {
                        val encodedName = URLEncoder.encode(company.name, "UTF-8")
                        navController.navigate("buy_stock_name/$encodedName")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Most Traded on Sphere Section ──
        Text(
            text = stringResource(R.string.most_traded_on_sphere),
            style = AppTextStyles.bold(15),
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Show max 7 companies + 1 "View All" card => up to 8 items in 2-column grid
        val displayCompanies = companiesWithTicks.take(7)
        val totalItems = displayCompanies.size + 1 // +1 for "View All"
        val rows = (totalItems + 1) / 2 // ceiling division by 2

        for (rowIndex in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (colIndex in 0 until 2) {
                    val itemIndex = rowIndex * 2 + colIndex
                    Box(modifier = Modifier.weight(1f)) {
                        when {
                            itemIndex < displayCompanies.size -> {
                                val company = displayCompanies[itemIndex]
                                StockGridCard(
                                    company = company,
                                    tick = marketTicks[company.symbol],
                                    onClick = {
                                        val encodedName = URLEncoder.encode(company.name, "UTF-8")
                                        navController.navigate("buy_stock_name/$encodedName")
                                    }
                                )
                            }
                            itemIndex == displayCompanies.size -> {
                                SeeAllCard(
                                    onClick = {
                                        navController.navigate(ALL_STOCKS)
                                    }
                                )
                            }
                            // else: empty cell to fill the row
                        }
                    }
                }
            }
            if (rowIndex < rows - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}