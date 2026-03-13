package com.assignments.stockmarket

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignments.stockmarket.bottom_navigation.screens.BuyStockScreen
import com.assignments.stockmarket.bottom_navigation.screens.FAndQScreen
import com.assignments.stockmarket.bottom_navigation.screens.LoansScreen
import com.assignments.stockmarket.bottom_navigation.screens.MutualFundsScreen
import com.assignments.stockmarket.bottom_navigation.screens.UPIScreen
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

        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable(
            route = "otp/{email}/{otp}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("otp") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            OTPScreen(
                navController = navController,
                email = email,
                expectedOtp = otp
            )
        }

        composable("mpin_finger_print") {
            MPINFingerPrintScreen(navController)
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("search") {
            SearchScreen(navController)
        }

        composable("f_and_o") {
            FAndQScreen(navController)
        }

        composable("mutual_funds") {
            MutualFundsScreen(navController)
        }

        composable("upi") {
            UPIScreen(navController)
        }

        composable("loans") {
            LoansScreen(navController)
        }

        composable("buy_stock") {
            BuyStockScreen(navController)
        }
    }
}