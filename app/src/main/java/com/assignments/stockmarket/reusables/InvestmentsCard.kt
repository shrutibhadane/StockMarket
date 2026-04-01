package com.assignments.stockmarket.reusables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun InvestmentsCard(
    investedValue: String,
    totalReturns: String,
    arrowClick: () -> Unit,
    ) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A70)),
        border = BorderStroke(2.dp, colorResource(R.color.white)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Invested",
                    color = colorResource(R.color.white),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = investedValue,
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Column for Total Returns text and value
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Total Returns",
                        color = colorResource(R.color.white),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = totalReturns,
                        color = colorResource(R.color.text_error_light),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Icon at the far right
                Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow), // your right arrow icon
                    contentDescription = "Go",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(24.dp)
                    .clickable { arrowClick() }, // dynamic click

                )
            }
        }
    }
}