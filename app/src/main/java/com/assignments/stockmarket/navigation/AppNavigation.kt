package com.assignments.stockmarket.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignments.stockmarket.DashboardScreen
import com.assignments.stockmarket.ForgotPasswordScreen
import com.assignments.stockmarket.LoginScreen
import com.assignments.stockmarket.MPINFingerPrintScreen
import com.assignments.stockmarket.MPINScreen
import com.assignments.stockmarket.OTPScreen
import com.assignments.stockmarket.ResetPasswordScreen
import com.assignments.stockmarket.SignUpScreen
import com.assignments.stockmarket.SplashScreen
import com.assignments.stockmarket.WelcomeScreen
import com.assignments.stockmarket.bottom_navigation.screens.BuyStockScreen
import com.assignments.stockmarket.bottom_navigation.screens.FAndQScreen
import com.assignments.stockmarket.bottom_navigation.screens.LoansScreen
import com.assignments.stockmarket.bottom_navigation.screens.MutualFundsScreen
import com.assignments.stockmarket.bottom_navigation.screens.UPIScreen
import com.assignments.stockmarket.change_password.ChangePasswordScreen
import com.assignments.stockmarket.investments.InvestmentScreen
import com.assignments.stockmarket.notifications.NotificationSettingsScreen
import com.assignments.stockmarket.profile.ProfileScreen
import com.assignments.stockmarket.search.SearchScreen
import com.assignments.stockmarket.security.SecurityScreen
import com.assignments.stockmarket.settings.SettingsScreen
import com.assignments.stockmarket.tabs.explore.ExploreScreen
import com.assignments.stockmarket.tabs.holdings.HoldingsScreen
import com.assignments.stockmarket.tabs.orders.OrdersScreen
import com.assignments.stockmarket.tabs.positions.PositionsScreen

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

        composable(
            route = "buy_stock/{stockId}"
        ) { backStackEntry ->

            val stockId = backStackEntry.arguments?.getString("stockId")?.toInt() ?: 0

            BuyStockScreen(
                navController = navController,
                stockId = stockId
            )
        }

        composable("explore") {
            ExploreScreen(navController)
        }

        composable("holdings") {
            HoldingsScreen(navController)
        }

        composable("positions") {
            PositionsScreen(navController)
        }

        composable("orders") {
            OrdersScreen(navController)
        }

        composable("investment_amount") {
            InvestmentScreen(navController)
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("settings") {
            SettingsScreen(navController)
        }

        composable("notification_settings") {
            NotificationSettingsScreen(navController)
        }

        composable("security_and_login") {
            SecurityScreen(navController)
        }

        composable("change_password") {
            ChangePasswordScreen(navController)
        }

    }
}