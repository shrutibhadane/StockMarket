package com.assignments.stockmarket.tabs.explore

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

private val priceCardFormat = DecimalFormat("#,##0.00")
private val changeCardFormat = DecimalFormat("0.00")

/**
 * A single grid card for the "Most Traded on Sphere" section.
 * Displays company logo (via Glide), name, current price,
 * price change (absolute + percentage), updated live every 3 seconds.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StockGridCard(
    company: CompanyEntity,
    tick: MarketTick? = null,
    onClick: () -> Unit = {}
) {
    val currentPrice = tick?.price ?: 0.0
    val previousPrice = tick?.previousPrice ?: 0.0
    val priceDiff = currentPrice - previousPrice
    val isPositive = priceDiff >= 0.0
    val percentChange = if (previousPrice != 0.0) {
        (priceDiff / previousPrice) * 100.0
    } else 0.0

    val sign = if (isPositive) "+" else "-"
    val changeText = "${sign}${changeCardFormat.format(abs(priceDiff))} (${changeCardFormat.format(abs(percentChange))}%)"

    val changeColor = if (isPositive) {
        colorResource(R.color.text_success_light)
    } else {
        colorResource(R.color.text_error)
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF253657)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            // Company Logo via Glide
            GlideImage(
                model = company.logo,
                contentDescription = company.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = colorResource(R.color.text_accent_blue_light),
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Company Name (single line, ellipsis if too long)
            Text(
                text = company.name,
                style = AppTextStyles.bold(14),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Current Price
            if (tick != null) {
                Text(
                    text = "₹${priceCardFormat.format(currentPrice)}",
                    style = AppTextStyles.semiBold(13),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price Change + Percentage
                Text(
                    text = changeText,
                    style = AppTextStyles.medium(11, changeColor),
                    maxLines = 1
                )
            } else {
                // No live data yet
                Text(
                    text = stringResource(R.string.dash),
                    style = AppTextStyles.medium(13,
                    colorResource(R.color.white).copy(alpha = 0.6f),
                    )
                )
            }
        }
    }
}

/**
 * Legacy StockGridCard that accepts a GridStock for backward compatibility.
 */
@Composable
fun StockGridCardLegacy(stock: GridStock) {
    val changeColor =
        if (stock.isPositive)
            colorResource(R.color.text_success_light)
        else
            colorResource(R.color.text_error)

    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF253657)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stock.name,
                style = AppTextStyles.bold(14),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stock.price,
                style = AppTextStyles.bold(14),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stock.change,
                style = AppTextStyles.medium(11, changeColor),
            )
        }
    }
}