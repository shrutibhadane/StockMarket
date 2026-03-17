package com.assignments.stockmarket.profile.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun BottomInfoRow() {

    val context = LocalContext.current
    val version = "1.1.1"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically // vertically center all items
    ) {

        Text(
            text = stringResource(R.string.about_us),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white),
            fontFamily = PoppinsFamily,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.trial_link))
                )
                context.startActivity(intent)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp)) // horizontal gap between icon and text

            Text(
                text = stringResource(R.string.version, version),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily
            )
        }

        Text(
            text = stringResource(R.string.charges),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white),
            fontFamily = PoppinsFamily,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.trial_link))
                )
                context.startActivity(intent)
            }
        )
    }
}