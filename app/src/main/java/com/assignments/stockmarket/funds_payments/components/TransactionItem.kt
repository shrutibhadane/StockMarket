package com.assignments.stockmarket.funds_payments.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun TransactionItem(
    title: String,
    amount: String,
    date: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = title,
                style = AppTextStyles.bold(13),
            )

            Text(
                text = date,
                style = AppTextStyles.bold(11, colorResource(R.color.white).copy(alpha = 0.6f))
            )
        }

        Text(
            text = amount,
            style = AppTextStyles.bold(13),
            color = if (amount.startsWith("+"))
                Color.Green
            else
                Color.Red,
        )
    }

    HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.2f))
}