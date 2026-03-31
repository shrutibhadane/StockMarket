package com.assignments.stockmarket.refer_and_earn.components

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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

@Composable
fun HowItWorksItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("• ",
            color = colorResource(R.color.white),
            fontSize = 12.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal)

        Text(
            text,
            color = colorResource(R.color.white),
            fontSize = 12.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal
        )
    }
}