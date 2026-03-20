package com.assignments.stockmarket.investments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.investments.components.AmountBox
import com.assignments.stockmarket.investments.components.LetterCircle
import com.assignments.stockmarket.investments.components.NumberKeypad
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.bottom_bar.BottomBarButtons
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun InvestmentScreen(navController: NavController) {

    var amount by remember { mutableStateOf(0L) }

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController,
                stringResource(R.string.one_time),
                stringResource(R.string.franklin_india_opportunities_direct_fund_growth)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                BottomBarButtons(
                    stringResource(R.string.add_to_wallet),
                    stringResource(R.string.invest_now),
                    { navController.navigate(R.string.login) },
                    { navController.navigate(R.string.search) },
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(horizontal = 0.dp, vertical = 0.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.investment_amount),
                color = colorResource(R.color.white),
                fontSize = 10.sp,
                lineHeight = 10.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.rs, amount),
                color = colorResource(R.color.white),
                fontSize = 30.sp,
                lineHeight = 30.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
            )

            Spacer(modifier = Modifier.height(70.dp))

            Row {
                AmountBox(stringResource(R.string._5_000)) { amount += 5000 }

                Spacer(modifier = Modifier.width(12.dp))

                AmountBox(stringResource(R.string._10_000)) { amount += 10000 }

                Spacer(modifier = Modifier.width(12.dp))

                AmountBox(stringResource(R.string._30_000)) { amount += 30000 }
            }

            Spacer(modifier = Modifier.height(50.dp))

            HorizontalDivider(
                color = colorResource(R.color.white),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LetterCircle("B")

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = stringResource(R.string.selected_upi_source_app_name),
                        color = colorResource(R.color.white),
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.selected_bank_name),
                        color = colorResource(R.color.white),
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            HorizontalDivider(
                color = colorResource(R.color.white),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            NumberKeypad(
                onNumberClick = { number ->

                    amount = if (amount == 0L) {
                        number.toLong()
                    } else {
                        (amount.toString() + number).toLong()
                    }

                },
                onDeleteClick = {

                    amount = if (amount.toString().length > 1) {
                        amount.toString().dropLast(1).toLong()
                    } else {
                        0L
                    }

                }
            )
        }

    }
}
