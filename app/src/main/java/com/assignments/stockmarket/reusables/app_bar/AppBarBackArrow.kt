package com.assignments.stockmarket.reusables.app_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarBackArrow(
    navController: NavController,
    title: String? = null,
    subTitle: String? = null,
    showSettings: Boolean = false
) {

    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorResource(id = R.color.screen_background)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.screen_background),
            scrolledContainerColor = colorResource(R.color.white),
            titleContentColor = colorResource(R.color.white),
            actionIconContentColor = colorResource(R.color.white),
        ),

        navigationIcon = {
            IconButton(onClick = {
                if (navController.previousBackStackEntry == null) {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                } else {
                    navController.popBackStack()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow), // Use your back arrow icon
                    contentDescription = "Back",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(24.dp)
                )
            }
        },

        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        color = colorResource(R.color.white),
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                if (subTitle != null) {
                    Text(
                        text = subTitle,
                        color = colorResource(R.color.white),
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                    )
                }
            }
        },

        actions = {
            if (showSettings) {
                IconButton(onClick = {
                    navController.navigate("settings")
                }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
    )
}

