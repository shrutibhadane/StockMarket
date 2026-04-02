package com.assignments.stockmarket.tabs.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.tabs.holdings.Holding
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun OrderItemCard(orders: Orders) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.bg_text_field)
        ),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.25f)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = orders.time,
                    style = AppTextStyles.bold(8, colorResource(R.color.text_secondary)),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = orders.status,
                    style = AppTextStyles.bold(8, colorResource(R.color.text_success)),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = orders.name,
                    style = AppTextStyles.bold(12),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = orders.quantity,
                    style = AppTextStyles.bold(10),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = orders.type,
                    style = AppTextStyles.bold(8, colorResource(R.color.text_secondary)),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = orders.avgValue,
                    style = AppTextStyles.bold(8, colorResource(R.color.text_secondary)),
                )
            }
        }
    }
}