package com.assignments.stockmarket.reusables.bottom_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun BottomBarButtons(
    leftButtonText: String,
    rightButtonText: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    leftIconRes: Int? = null,
    rightIconRes: Int? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.bg_primary))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onLeftClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.bg_button_secondary_light)
            ),
            modifier = Modifier.weight(1f)
        ) {
            leftIconRes?.let { icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = leftButtonText,
                style = AppTextStyles.bold(14,
                    colorResource(R.color.text_link)
                ),
            )
        }

        Button(
            onClick = onRightClick,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, colorResource(R.color.white)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.bg_button_secondary_dark)),
            modifier = Modifier.weight(1f)
        ) {
            rightIconRes?.let { icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = rightButtonText,
                style = AppTextStyles.bold(14),
            )
        }
    }

}