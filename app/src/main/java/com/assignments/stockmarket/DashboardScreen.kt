package com.assignments.stockmarket

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.reusables.MyAppBar
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import org.w3c.dom.Text

@Composable
fun DashboardScreen(
    navController: NavController,
) {
    val bottomNavItems = listOf("Stocks", "F&O", "Mutual Funds", "UPI", "Loans")
    val selectedTabIndex = remember { mutableStateOf(0) }
    val tabItems = listOf("Explore", "Holdings", "Positions", "Orders")

    Scaffold(
        topBar = { MyAppBar() }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(innerPadding)  // Important to apply scaffold's padding
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
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
    ) {
        Column(
            modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Center
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFamily,
                fontSize = 10.sp,
                color = colorResource(R.color.black)
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
                    color = colorResource(id = R.color.black)
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


