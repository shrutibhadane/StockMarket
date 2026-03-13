package com.assignments.stockmarket.bottom_navigation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.InvestmentsCard
import com.assignments.stockmarket.reusables.MFImageBar
import com.assignments.stockmarket.reusables.bottom_bar.BottomBarButtons
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun BuyStockScreen(navController: NavController) {

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

            // Candlestick Chart Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        Color(0xFF122B55),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Example: Draw simple candlesticks (replace with actual chart)
                    val candleWidth = size.width / 20
                    for (i in 0 until 15) {
                        val x = i * candleWidth * 1.2f
                        val candleHeight = (20..80).random().toFloat()
                        val top = size.height - candleHeight
                        drawRect(
                            color = if (i % 2 == 0) Color(0xFF00FF00) else Color.Red,
                            topLeft = Offset(x, top),
                            size = Size(candleWidth, candleHeight)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            // Time selection buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("1M", "6M", "1Y", "3Y", "5Y", "ALL").forEach { period ->
                    val isSelected = period == "3Y"
                    Text(
                        text = period,
                        color = if (isSelected) Color(0xFF00FF00) else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
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