package com.assignments.stockmarket.reusables.app_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarBackArrow(
    navController: NavController,
    title: String? = null,
    subTitle: String? = null,
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
                // Check if we are on the Home screen (the root screen)
                if (navController.previousBackStackEntry == null) {
                    // Navigate directly to Home if we are already on the root screen
                    navController.navigate("dashboard") {
                        // Clear the back stack to prevent going back further
                        popUpTo("dashboard") { inclusive = true }
                    }
                } else {
                    // Regular back navigation
                    navController.popBackStack()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow), // Use your back arrow icon
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },

        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        color = colorResource(R.color.white),
                        fontSize = 12.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (subTitle != null) {
                    Text(
                        text = subTitle,
                        color = colorResource(R.color.white),
                        fontSize = 8.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                    )
                }
            }
        },
    )
}

