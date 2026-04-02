package com.assignments.stockmarket.funds_payments.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
fun BankAccountItem(
    bankName: String,
    accountNumber: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.AccountBalance,
            contentDescription = null,
            tint = colorResource(R.color.white)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = bankName,
                style = AppTextStyles.bold(13),
                )

            Text(
                text = accountNumber,
                style = AppTextStyles.bold(11, colorResource(R.color.white).copy(alpha = 0.6f))
            )
        }
    }

    HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.2f))
}