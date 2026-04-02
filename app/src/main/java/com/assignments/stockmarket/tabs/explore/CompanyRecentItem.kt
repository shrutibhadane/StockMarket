package com.assignments.stockmarket.tabs.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.MarketTick
import com.assignments.stockmarket.R
import com.assignments.stockmarket.db.CompanyEntity
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.text.DecimalFormat
import kotlin.math.abs

private val pctFormat = DecimalFormat("0.00")
private val priceFormat = DecimalFormat("#,##0.00")

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyRecentItem(
    company: CompanyEntity,
    tick: MarketTick? = null,
    onClick: () -> Unit = {}
) {
    // Calculate percentage change from live tick
    val change = if (tick != null && tick.previousPrice != 0.0) {
        ((tick.price - tick.previousPrice) / tick.previousPrice) * 100.0
    } else {
        null
    }
    val isPositive = (change ?: 0.0) >= 0.0
    val changeColor = if (isPositive) {
        colorResource(R.color.text_success_light)
    } else {
        colorResource(R.color.text_error)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { onClick() }
    ) {

        // Company logo loaded via Glide
        GlideImage(
            model = company.logo,
            contentDescription = company.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = colorResource(R.color.text_accent_blue_light),
                    shape = RoundedCornerShape(10.dp)
                )
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Company symbol (short name e.g. RELIANCE, TCS)
        Text(
            text = company.symbol,
            style = AppTextStyles.medium(11),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Live price + percentage change
        if (tick != null) {
            // Price text (e.g. ₹2,920.50)
            Text(
                text = "₹${priceFormat.format(tick.price)}",
                style = AppTextStyles.regular(9,
                    colorResource(R.color.white).copy(alpha = 8.5f)),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            // Percentage change (e.g. +0.15%)
            if (change != null) {
                val sign = if (isPositive) "+" else "-"
                Text(
                    text = "${sign}${pctFormat.format(abs(change))}%",
                    style = AppTextStyles.semiBold(10, changeColor),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        } else {
            // No live data yet — show placeholder
            Text(
                text = stringResource(R.string.dash),
                style = AppTextStyles.regular(10,
                    colorResource(R.color.white).copy(alpha = 0.6f)),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
