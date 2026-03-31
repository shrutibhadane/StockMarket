package com.assignments.stockmarket.reusables.app_bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.navigation.Routes.PROFILE
import com.assignments.stockmarket.navigation.Routes.SEARCH
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    navController: NavController
) {

    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorResource(id = R.color.bg_primary)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.bg_primary),
            scrolledContainerColor = colorResource(R.color.white),
            titleContentColor = colorResource(R.color.white),
            actionIconContentColor = colorResource(R.color.white),
        ),

        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_stock_logo),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.my_sphere),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    fontFamily = PoppinsFamily
                )
            }
        },

        actions = {
            IconButton(onClick = {
                navController.navigate(SEARCH)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = SEARCH,
                    tint = colorResource(R.color.white),

                )
            }

            IconButton(onClick = { navController.navigate(PROFILE) }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.section_profile),
                    tint = colorResource(R.color.white),
                )
            }
        }
    )
}

