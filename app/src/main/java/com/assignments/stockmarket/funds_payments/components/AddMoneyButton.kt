package com.assignments.stockmarket.funds_payments.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun AddMoneyButton() {

    Button(
        onClick = { },
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.white)
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Money",
                modifier = Modifier.size(14.dp),
                tint = colorResource(R.color.light_blue_button_bg_color)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Add",
                fontFamily = PoppinsFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.light_blue_button_bg_color)
            )
        }
    }
}