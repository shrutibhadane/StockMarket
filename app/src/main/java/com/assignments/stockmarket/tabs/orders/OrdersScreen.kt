package com.assignments.stockmarket.tabs.orders

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
import com.assignments.stockmarket.R
import com.assignments.stockmarket.tabs.holdings.Holding
import com.assignments.stockmarket.tabs.holdings.HoldingItemCard
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun OrdersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Today's Equity Orders (2)",
            color = colorResource(R.color.white),
            fontSize = 15.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val orders = listOf(
            Orders(
                time = "12:53 PM",
                status = "Buy",
                name = "Stock 1",
                quantity = "Qty-3",
                type = "Delivery",
                avgValue = "Avg Rs 1879.99",

            ),
            Orders(
                time = "10:11 PM",
                status = "Buy",
                name = "Stock 2",
                quantity = "Qty-3",
                type = "Delivery",
                avgValue = "Avg Rs 7158.99",
            ),
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(orders) { order ->
                OrderItemCard(order)
            }
        }

    }
}