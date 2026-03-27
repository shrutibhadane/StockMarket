package com.assignments.stockmarket.tabs.holdings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun HoldingsScreen(navController: NavController) {

    Column(
        Modifier.padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.bg_text_field)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "HOLDINGS (2)",
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rs. 3403.75",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(colorResource(R.color.bg_text_field))
                                .border(1.dp, colorResource(R.color.icon_tint_light), CircleShape)
                                .clickable {
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = stringResource(R.string.label_forgot_password),
                                tint = colorResource(R.color.icon_tint_light),
                                modifier = Modifier.size(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(colorResource(R.color.bg_text_field))
                                .border(1.dp, colorResource(R.color.icon_tint_light), CircleShape)
                                .clickable {
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = stringResource(R.string.label_forgot_password),
                                tint = colorResource(R.color.icon_tint_light),
                                modifier = Modifier.size(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(colorResource(R.color.bg_text_field))
                                .border(1.dp, colorResource(R.color.icon_tint_light), CircleShape)
                                .clickable {
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.label_forgot_password),
                                tint = colorResource(R.color.icon_tint_light),
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Divider(color = Color.Gray.copy(alpha = 0.3f))

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),

                    ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "1D Returns",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            color = colorResource(R.color.text_secondary),
                            textAlign = TextAlign.Left
                        )

                        Text(
                            text = "-Rs 63.67 (1.86%)",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            color = colorResource(R.color.text_error_light),
                            textAlign = TextAlign.Right
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Total Returns",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            color = colorResource(R.color.text_secondary),
                            textAlign = TextAlign.Left
                        )

                        Text(
                            text = "-Rs 160.24 (4.54%)",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            color = colorResource(R.color.text_error_light),
                            textAlign = TextAlign.Right
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Invested",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,
                        color = colorResource(R.color.text_secondary),
                        textAlign = TextAlign.Left
                    )

                    Text(
                        text = "Rs. 3,556.09",
                        fontFamily = PoppinsFamily,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Holding Sphere",
            color = colorResource(R.color.white),
            fontSize = 15.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val holdings = listOf(
            Holding(
                name = "SILVERBEES",
                shares = "7 shares",
                pnl = "-Rs 1721.35",
                value = "(Rs 1,879.99)"
            ),
            Holding(
                name = "HINDUSTAN COPPER",
                shares = "3 shares",
                pnl = "-Rs 1669.90",
                value = "(Rs 1,676.10)"
            )
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(holdings) { holding ->
                HoldingItemCard(holding)
            }
        }

    }
}