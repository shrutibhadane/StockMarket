package com.assignments.stockmarket.tabs.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun RecentlyViewedItem(stockDetails: StockDetails, navController: NavController) {

    val changeColor =
        if (stockDetails.isPositive)
            colorResource(R.color.text_success_light)
        else
            colorResource(R.color.text_error)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = colorResource(R.color.text_accent_blue_light),
                    shape = RoundedCornerShape(10.dp)
                )
            .clickable {
                navController.navigate("buy_stock/${stockDetails.id}")
                },
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.Black, CircleShape)
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stockDetails.name,
            fontSize = 13.sp,
            color = colorResource(R.color.white),
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = stockDetails.annualReturn.toString(),
            fontSize = 12.sp,
            color = changeColor,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Medium
        )
    }
}