package com.assignments.stockmarket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.bottom_bar.BottomBarButtons

@Composable
fun InvestmentScreen(navController: NavController) {

    Scaffold(
        topBar = { AppBarBackArrow(navController, "One Time", "Franklin India Opportunities Direct Fund Growth")},
        bottomBar = {
            BottomBarButtons(
                "Add to cart",
                "Invest Now",
                { navController.navigate("login") },
                { navController.navigate("search") },
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.screen_background))
                .padding(horizontal = 12.dp, vertical = 0.dp)
                .padding(innerPadding)
        ) {
        }

        }
}
