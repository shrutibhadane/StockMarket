package com.assignments.stockmarket.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun BalanceCard(navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.screen_background),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

       Icon(
           imageVector = Icons.Default.Wallet,
           contentDescription = stringResource(R.string.wallet),
           tint = colorResource(R.color.white),
           modifier = Modifier.size(20.dp)
       )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = stringResource(R.string._0_00),
                color =  colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = stringResource(R.string.stocks_f_o_balance),
                color =  colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Button(
            onClick = {
                navController.navigate("investment_amount")
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.light_blue_button_bg_color)
            ),
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .width(100.dp)
                .height(28.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Money,
                    contentDescription = stringResource(R.string.add_money),
                    modifier = Modifier.size(16.dp),
                    tint = colorResource(R.color.screen_background),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(R.string.add_money),
                    color = colorResource(R.color.screen_background),
                    fontFamily = PoppinsFamily,
                    fontSize =  9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}