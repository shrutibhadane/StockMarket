package com.assignments.stockmarket

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        composable("sign_up") {
            SignUpScreen(navController)
        }

        composable("mpin") {
            MPINScreen(navController)
        }

        composable("otp") {
            OTPScreen(navController)
        }

        composable("mpin_finger_print") {
            MPINFingerPrintScreen(navController)
        }

    }
}