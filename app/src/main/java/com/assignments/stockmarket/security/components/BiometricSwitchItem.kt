package com.assignments.stockmarket.security.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun BiometricSwitchItem(title: String) {

    var enabled by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Fingerprint,
            contentDescription = null,
            tint = colorResource(R.color.white),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            style = AppTextStyles.bold(14),
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = enabled,
            onCheckedChange = { enabled = it }
        )
    }

    HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.2f))
}