package com.assignments.stockmarket.tabs.positions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.tabs.holdings.Holding
import com.assignments.stockmarket.tabs.holdings.HoldingItemCard
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun PositionsScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Position in Market",
            style = AppTextStyles.bold(15),
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val holdings = listOf(
            Holding(
                name = "Stock 1",
                shares = "7 shares",
                pnl = "-Rs 1721.35",
                value = "(Rs 1,879.99)"
            ),
            Holding(
                name = "Stock 2",
                shares = "3 shares",
                pnl = "-Rs 1669.90",
                value = "(Rs 1,676.10)"
            ),
            Holding(
                name = "Stock 3",
                shares = "7 shares",
                pnl = "-Rs 1721.35",
                value = "(Rs 1,879.99)"
            ),
            Holding(
                name = "Stock 4",
                shares = "3 shares",
                pnl = "-Rs 1669.90",
                value = "(Rs 1,676.10)"
            ),
            Holding(
                name = "Stock 5",
                shares = "7 shares",
                pnl = "-Rs 1721.35",
                value = "(Rs 1,879.99)"
            ),
            Holding(
                name = "Stock 6",
                shares = "3 shares",
                pnl = "-Rs 1669.90",
                value = "(Rs 1,676.10)"
            ),
            Holding(
                name = "Stock 7",
                shares = "7 shares",
                pnl = "-Rs 1721.35",
                value = "(Rs 1,879.99)"
            ),
            Holding(
                name = "Stock 8",
                shares = "3 shares",
                pnl = "-Rs 1669.90",
                value = "(Rs 1,676.10)"
            )
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(holdings) { holding ->
                HoldingItemCard(holding)
            }
        }

    }
}