package com.assignments.stockmarket.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.notifications.components.NotificationSectionTitle
import com.assignments.stockmarket.notifications.components.NotificationSwitchItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow

@Composable
fun NotificationSettingsScreen(navController: NavController) {

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController,
                title = "Notifications"
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(innerPadding)
        ) {

            item { NotificationSectionTitle(stringResource(R.string.trading_alerts)) }

            item {
                NotificationSwitchItem(stringResource(R.string.order_executed))
            }

            item {
                NotificationSwitchItem(stringResource(R.string.order_rejected))
            }

            item {
                NotificationSwitchItem(stringResource(R.string.price_alerts))
            }

            item { NotificationSectionTitle(stringResource(R.string.portfolio_updates)) }

            item {
                NotificationSwitchItem(stringResource(R.string.portfolio_performance))
            }

            item {
                NotificationSwitchItem(stringResource(R.string.dividend_updates))
            }

            item { NotificationSectionTitle(stringResource(R.string.general)) }

            item {
                NotificationSwitchItem(stringResource(R.string.market_news))
            }

            item {
                NotificationSwitchItem(stringResource(R.string.offers_promotions))
            }

            item {
                NotificationSwitchItem(stringResource(R.string.app_updates))
            }
        }
    }
}