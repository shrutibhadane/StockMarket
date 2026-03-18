package com.assignments.stockmarket.about_us

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.about_us.components.AboutItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.ui.theme.PoppinsFamily


@Composable
fun AboutScreen(navController: NavController) {

    val context = LocalContext.current
    val version = "1.1.1"

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController = navController,
                title = stringResource(R.string.about_us)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(R.drawable.ic_stock_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.app_name),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(R.color.white)
            )

            Text(
                text = stringResource(R.string.version, version),
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = colorResource(R.color.white).copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.stock_market_is_a_modern_investment_platform_designed_to_help_users_track_markets_manage_investments_and_trade_with_confidence),
                fontFamily = PoppinsFamily,
                fontSize = 13.sp,
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(20.dp))

            AboutItem(
                title = stringResource(R.string.privacy_policy),
                url = stringResource(R.string.trial_link),
            )

            AboutItem(
                title = stringResource(R.string.terms_conditions),
                url = stringResource(R.string.trial_link),
            )

            AboutItem(
                title = stringResource(R.string.visit_website),
                url = stringResource(R.string.trial_link),
            )

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string._2025_stock_market_app),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = colorResource(R.color.white).copy(alpha = 0.6f)
            )
        }
    }
}