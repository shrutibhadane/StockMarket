package com.assignments.stockmarket.funds_payments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun BalanceCard() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.bg_button_secondary_light),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "₹0.00",
                style = AppTextStyles.bold(18, colorResource(R.color.bg_primary)),
                )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Available balance",
                style = AppTextStyles.bold(12, colorResource(R.color.bg_primary).copy(alpha = 0.7f)),
            )
        }

        AddMoneyButton()
    }
}