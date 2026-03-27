package com.assignments.stockmarket.tabs.explore

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.MarketTick
import com.assignments.stockmarket.R
import com.assignments.stockmarket.WebSocketManager
import com.assignments.stockmarket.db.CompanyEntity
import com.assignments.stockmarket.db.CompanyRepository
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.net.URLEncoder
import java.text.DecimalFormat
import kotlin.math.abs

private val allStocksPriceFormat = DecimalFormat("#,##0.00")
private val allStocksChangeFormat = DecimalFormat("0.00")

@Composable
fun AllStocksScreen(navController: NavController) {

    val context = LocalContext.current

    // Load companies from Room DB
    var companies by remember { mutableStateOf<List<CompanyEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        companies = CompanyRepository.getCompanies(context)
    }

    // Observe live ticks from WebSocket (already connected from DashboardScreen)
    val marketTicks by WebSocketManager.marketTicks.collectAsState()

    Scaffold(
        topBar = { AppBarBackArrow(navController, "All Stocks")
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            // Company list — one card per row
            items(companies) { company ->
                AllStockListItem(
                    company = company,
                    tick = marketTicks[company.symbol],
                    onClick = {
                        val encodedName = URLEncoder.encode(company.name, "UTF-8")
                        navController.navigate("buy_stock_name/$encodedName")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // End of List
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "End of List",
                    color = colorResource(R.color.light_grey_text_color),
                    fontSize = 13.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AllStockListItem(
    company: CompanyEntity,
    tick: MarketTick? = null,
    onClick: () -> Unit = {}
) {
    val currentPrice = tick?.price ?: 0.0
    val previousPrice = tick?.previousPrice ?: 0.0
    val priceDiff = currentPrice - previousPrice
    val isPositive = priceDiff >= 0.0
    val percentChange = if (previousPrice != 0.0) {
        (priceDiff / previousPrice) * 100.0
    } else 0.0

    val sign = if (isPositive) "+" else "-"
    val changeText = "${sign}${allStocksChangeFormat.format(abs(priceDiff))} (${allStocksChangeFormat.format(abs(percentChange))}%)"
    val changeColor = if (isPositive) {
        colorResource(R.color.light_green_text_color)
    } else {
        colorResource(R.color.red_text_color)
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF253657)
        ),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Company Logo
            GlideImage(
                model = company.logo,
                contentDescription = company.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = colorResource(R.color.extra_light_blue_text_color),
                        shape = RoundedCornerShape(10.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Name + Symbol
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = company.name,
                    color = colorResource(R.color.white),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = company.symbol,
                    color = colorResource(R.color.light_grey_text_color),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Price + Change (right-aligned)
            Column(horizontalAlignment = Alignment.End) {
                if (tick != null) {
                    Text(
                        text = "₹${allStocksPriceFormat.format(currentPrice)}",
                        color = colorResource(R.color.white),
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                    Text(
                        text = changeText,
                        color = changeColor,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                } else {
                    Text(
                        text = "—",
                        color = colorResource(R.color.white).copy(alpha = 0.6f),
                        fontFamily = PoppinsFamily,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

