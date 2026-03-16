package com.assignments.stockmarket

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignments.stockmarket.search.SearchScreen

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

        composable("mpin_verify") {
            MPINScreen(navController, isVerifyMode = true)
        }

        composable("mpin_reset") {
            MPINScreen(navController, isResetMode = true)
        }

        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable(
            route = "otp/{email}/{otp}?isResetMpin={isResetMpin}&isForgotPassword={isForgotPassword}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("otp") { type = NavType.StringType },
                navArgument("isResetMpin") {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument("isForgotPassword") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            val isResetMpin = backStackEntry.arguments?.getBoolean("isResetMpin") ?: false
            val isForgotPassword = backStackEntry.arguments?.getBoolean("isForgotPassword") ?: false
            OTPScreen(
                navController = navController,
                email = email,
                expectedOtp = otp,
                isResetMpin = isResetMpin,
                isForgotPassword = isForgotPassword
            )
        }

        composable("mpin_finger_print") {
            MPINFingerPrintScreen(navController)
        }

        composable(
            route = "reset_password/{identifier}",
            arguments = listOf(
                navArgument("identifier") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val identifier = backStackEntry.arguments?.getString("identifier") ?: ""
            ResetPasswordScreen(
                navController = navController,
                identifier = identifier
            )
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("search") {
            SearchScreen(navController)
        }

    }
}