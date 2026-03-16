package com.assignments.stockmarket.tabs.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun ExploreScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val stocks = listOf(
            Stock("Mothers", "+0.77%", true),
            Stock("Reliance", "+4.92%", true),
            Stock("Eternals", "-1.02%", false),
            Stock("Dee Engin.", "+3.12%", true),
            Stock("Silverbees", "-8.32%", false)
        )

        val gridStocks = listOf(
            GridStock("Tejas", "Rs. 484.70", "+48.85 (0.77%)", true),
            GridStock("Silverbees", "Rs. 274.65", "+21.84 (8.64%)", true),
            GridStock("Paras Defence", "Rs. 670.85", "+33.05 (5.18%)", true),
            GridStock("ONGC", "Rs. 282.20", "+2.55 (0.89%)", true),
            GridStock("Netweb Tech", "Rs. 3705.00", "-156.40 (4.44%)", false)
        )


        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Recently Viewed",
            color = colorResource(R.color.white),
            fontSize = 15.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            items(stocks) { stock ->
                RecentlyViewedItem(stock, navController)
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Most traded on Sphere",
            color = colorResource(R.color.white),
            fontSize = 15.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxHeight()
        ) {

            items(gridStocks) { stock ->
                StockGridCard(stock)
            }

            item {
                SeeAllCard()
            }
        }
    }
}