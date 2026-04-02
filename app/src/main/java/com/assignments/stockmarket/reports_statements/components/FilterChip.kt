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
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun FilterChip(label: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = colorResource(R.color.bg_button_secondary_light)
    ) {
        Text(
            text = label,
            style = AppTextStyles.semiBold(11,
                colorResource(R.color.bg_primary)
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}