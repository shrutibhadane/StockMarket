package com.assignments.stockmarket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds splash display

        // Check if email is stored in Paper NoSQL DB
        val storedEmail: String? = withContext(Dispatchers.IO) {
            Paper.book().read<String>("user_email")
        }

        if (storedEmail.isNullOrEmpty()) {
            // No stored email → go to login
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            // Email exists → call checkthestatus API
            val status = checkTheStatusApi(storedEmail)

            if (status.emailVerified) {
                // Check if MPIN is stored locally
                val storedMpin: String? = withContext(Dispatchers.IO) {
                    Paper.book().read<String>("user_mpin")
                }

                if (!storedMpin.isNullOrEmpty()) {
                    // email_verified = true & MPIN available → go to dashboard
                    navController.navigate("mpin_finger_print") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    // email_verified = true but no MPIN → go to MPIN screen
                    navController.navigate("mpin") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            } else {
                // email_verified = false → go to login
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.screen_background)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(300.dp)
            )

            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

