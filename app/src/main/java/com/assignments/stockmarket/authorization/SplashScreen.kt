package com.assignments.stockmarket.authorization

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.navigation.Routes.DASHBOARD
import com.assignments.stockmarket.navigation.Routes.LOGIN
import com.assignments.stockmarket.navigation.Routes.MPIN
import com.assignments.stockmarket.navigation.Routes.MPIN_VERIFY
import com.assignments.stockmarket.navigation.Routes.SPLASH
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds splash display

        // Check if email is stored in Paper NoSQL DB
        // Email is only saved after successful OTP verification,
        // so its presence means the user is already authenticated.
        val storedEmail: String? = withContext(Dispatchers.IO) {
            Paper.book().read<String>("user_email")
        }

        if (storedEmail.isNullOrEmpty()) {
            // No stored email → user has not verified yet → go to login
            navController.navigate(LOGIN) {
                popUpTo(SPLASH) { inclusive = true }
            }
        } else {
            // Email exists → user already verified via OTP, skip login/signup
            val storedMpin: String? = withContext(Dispatchers.IO) {
                Paper.book().read<String>("user_mpin")
            }

            if (!storedMpin.isNullOrEmpty()) {
                // MPIN available → check last login time
                val lastLoginTime: Long = withContext(Dispatchers.IO) {
                    Paper.book().read<Long>("last_login_time") ?: 0L
                }
                val hoursSinceLastLogin = (System.currentTimeMillis() - lastLoginTime) / (1000 * 60 * 60)

                if (hoursSinceLastLogin < 24 && lastLoginTime > 0L) {
                    // Last login within 24 hours → go directly to dashboard
                    navController.navigate(DASHBOARD) {
                        popUpTo(SPLASH) { inclusive = true }
                    }
                } else {
                    // Last login expired (>24 hours) → re-authorize MPIN
                    navController.navigate(MPIN_VERIFY) {
                        popUpTo(SPLASH) { inclusive = true }
                    }
                }
            } else {
                // No MPIN set yet → go to MPIN setup screen
                navController.navigate(MPIN) {
                    popUpTo(SPLASH) { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_primary)),
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
                style = AppTextStyles.bold(24),
                textAlign = TextAlign.Center
            )
        }
    }
}

