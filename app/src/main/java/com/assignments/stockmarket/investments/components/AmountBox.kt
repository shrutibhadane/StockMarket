package com.assignments.stockmarket.investments.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun AmountBox(amount: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.text_secondary),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onClick() }

        ) {

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "+ Rs. $amount",
                color = colorResource(R.color.text_secondary),
                fontSize = 10.sp,
                lineHeight = 10.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
            )
        }
    }
}