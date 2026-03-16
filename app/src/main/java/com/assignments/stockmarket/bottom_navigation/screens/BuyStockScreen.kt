package com.assignments.stockmarket.bottom_navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.graph.ScrollableCandleChart
import com.assignments.stockmarket.graph.candles
import com.assignments.stockmarket.reusables.InvestmentsCard
import com.assignments.stockmarket.reusables.MFImageBar
import com.assignments.stockmarket.reusables.bottom_bar.BottomBarButtons
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun BuyStockScreen(navController: NavController) {

    var selectedPeriod by remember { mutableStateOf("3Y") }

    Scaffold(
        bottomBar = {
            BottomBarButtons(
                "SELL",
                "BUY",
                { navController.navigate("login") },
                { navController.navigate("search") },
                R.drawable.red_dot,
                R.drawable.green_dot,
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(horizontal = 4.dp, vertical = 0.dp)
        ) {

            MFImageBar()

            Spacer(modifier = Modifier.height(16.dp))

            // Top section: Fund name & risk
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Very High Risk",
                        color = colorResource(R.color.light_grey_text_color),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,
                    )

                    Text(
                        text = " • ",
                        color = colorResource(R.color.light_grey_text_color),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,
                    )

                    Text(
                        text = "Equity",
                        color = colorResource(R.color.light_grey_text_color),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,
                    )

                    Text(
                        text = " • ",
                        color = colorResource(R.color.light_grey_text_color),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,
                    )

                    Text(
                        text = "Thematic",
                        color = colorResource(R.color.light_grey_text_color),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tejas Networks",
                    color = colorResource(R.color.white),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "28.24%",
                    color = colorResource(R.color.light_green_text_color),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "3Y annualised",
                    color = colorResource(R.color.light_grey_text_color),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "-2.07%",
                color = colorResource(R.color.light_red_text_color),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily,
            )

            Spacer(modifier = Modifier.height(12.dp))

            ScrollableCandleChart(
                candles = candles,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Time selection buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("1M", "6M", "1Y", "3Y", "5Y", "ALL").forEach { period ->
                    val isSelected = period == selectedPeriod
                    Text(
                        text = period,
                        color = if (isSelected) Color(0xFF00FF00) else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .clickable { selectedPeriod = period }
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Invested & Total Returns Section
            InvestmentsCard(
                investedValue = "Rs. 3,000",
                totalReturns = "-3.12%",
                arrowClick = { navController.navigate("dashboard") }
            )
        }
    }
}