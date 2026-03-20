package com.assignments.stockmarket.reports_statements.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun FilterChip(label: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = colorResource(R.color.light_blue_button_bg_color)
    ) {
        Text(
            text = label,
            color = colorResource(R.color.screen_background),
            fontSize = 11.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}