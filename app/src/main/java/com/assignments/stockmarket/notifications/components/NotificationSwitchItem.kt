package com.assignments.stockmarket.notifications.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun NotificationSwitchItem(title: String) {

    var isEnabled by remember { mutableStateOf(true) }

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                style = AppTextStyles.bold(14),
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled = it }
            )
        }

        HorizontalDivider(
            color = colorResource(R.color.white).copy(alpha = 0.2f)
        )
    }
}