package com.assignments.stockmarket.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.profile.components.BalanceCard
import com.assignments.stockmarket.profile.components.BottomInfoRow
import com.assignments.stockmarket.profile.components.ProfileHeader
import com.assignments.stockmarket.profile.components.ProfileMenuItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow

@Composable
fun ProfileScreen(navController: NavController) {

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController,
                showSettings = true,
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            ProfileHeader()

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(24.dp))

            BalanceCard(navController)

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))

            ProfileMenuItem(Icons.Default.Receipt, stringResource(R.string.orders))
            ProfileMenuItem(Icons.Default.Person, stringResource(R.string.account_details))
            ProfileMenuItem(Icons.Default.AccountBalance, stringResource(R.string.banks_autopay))

            ProfileMenuItem(icon = Icons.Default.Star, title = stringResource(R.string.refer),
                actionText = stringResource(
                    R.string.invite
                )
            )

            ProfileMenuItem(Icons.Default.SupportAgent, stringResource(R.string.customer_support_24x7))
            ProfileMenuItem(Icons.Default.Report, stringResource(R.string.reports))

            Spacer(modifier = Modifier.weight(1f))

            BottomInfoRow()
        }
    }
}
