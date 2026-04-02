package com.assignments.stockmarket.bottom_navigation.mutual_funds

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.graph.ScrollableCandleChart
import com.assignments.stockmarket.graph.candles
import com.assignments.stockmarket.navigation.Routes.DASHBOARD
import com.assignments.stockmarket.navigation.Routes.INVESTMENT_AMOUNT
import com.assignments.stockmarket.navigation.Routes.SEARCH
import com.assignments.stockmarket.reusables.InvestmentsCard
import com.assignments.stockmarket.reusables.MFImageBar
import com.assignments.stockmarket.reusables.bottom_bar.BottomBarButtons
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun MutualFundsScreen(navController: NavController) {

    var selectedPeriod by remember { mutableStateOf("3Y") }

    Scaffold(
        bottomBar = {
            BottomBarButtons(
                "One-Time",
                "Start SIP",
                { navController.navigate(INVESTMENT_AMOUNT) },
                { navController.navigate(SEARCH) },
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(horizontal = 4.dp, vertical = 0.dp)
        ) {

            MFImageBar()

            Spacer(modifier = Modifier.height(16.dp))

            // Top section: Fund name & risk
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Franklin India Opportunities Direct Fund Growth",
                    style = AppTextStyles.bold(17),
                    )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.very_high_risk),
                        style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )

                    Text(
                        text = stringResource(R.string.dot),
                        style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )

                    Text(
                        text = stringResource(R.string.equity),
                        style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )

                    Text(
                        text = stringResource(R.string.dot),
                        style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )

                    Text(
                        text = stringResource(R.string.thematic),
                        style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                        )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "28.24%",
                    style = AppTextStyles.bold(20, colorResource(R.color.text_success_light)),
                    )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "3Y annualised",
                    style = AppTextStyles.bold(10, colorResource(R.color.text_secondary)),
                    )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "-2.07%",
                style = AppTextStyles.bold(14, colorResource(R.color.text_error_light)),
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
                arrowClick = { navController.navigate(DASHBOARD) }
            )
        }
    }
}