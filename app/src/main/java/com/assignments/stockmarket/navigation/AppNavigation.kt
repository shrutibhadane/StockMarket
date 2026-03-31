package com.assignments.stockmarket.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignments.stockmarket.dashboard.DashboardScreen
import com.assignments.stockmarket.authorization.ForgotPasswordScreen
import com.assignments.stockmarket.authorization.LoginScreen
import com.assignments.stockmarket.authorization.MPINFingerPrintScreen
import com.assignments.stockmarket.authorization.MPINScreen
import com.assignments.stockmarket.authorization.OTPScreen
import com.assignments.stockmarket.authorization.ResetPasswordScreen
import com.assignments.stockmarket.authorization.SignUpScreen
import com.assignments.stockmarket.authorization.SplashScreen
import com.assignments.stockmarket.authorization.WelcomeScreen
import com.assignments.stockmarket.about_us.AboutScreen
import com.assignments.stockmarket.active_devices.ActiveDevicesScreen
import com.assignments.stockmarket.bottom_navigation.buy_stocks.BuyStockScreen
import com.assignments.stockmarket.bottom_navigation.f_and_q.FAndQScreen
import com.assignments.stockmarket.bottom_navigation.loans.LoansScreen
import com.assignments.stockmarket.bottom_navigation.mutual_funds.MutualFundsScreen
import com.assignments.stockmarket.bottom_navigation.upi.UPIScreen
import com.assignments.stockmarket.change_password.ChangePasswordScreen
import com.assignments.stockmarket.funds_payments.FundsPaymentsScreen
import com.assignments.stockmarket.help_support.HelpSupportScreen
import com.assignments.stockmarket.investments.InvestmentScreen
import com.assignments.stockmarket.kyc_verification.KYCScreen
import com.assignments.stockmarket.navigation.Routes.ABOUT
import com.assignments.stockmarket.navigation.Routes.ACTIVE_DEVICES
import com.assignments.stockmarket.navigation.Routes.ALL_STOCKS
import com.assignments.stockmarket.navigation.Routes.BUY_STOCK
import com.assignments.stockmarket.navigation.Routes.BUY_STOCK_NAME
import com.assignments.stockmarket.navigation.Routes.CHANGE_PASSWORD
import com.assignments.stockmarket.navigation.Routes.DASHBOARD
import com.assignments.stockmarket.navigation.Routes.EXPLORE
import com.assignments.stockmarket.navigation.Routes.FAndQ
import com.assignments.stockmarket.navigation.Routes.FORGOT_PASSWORD
import com.assignments.stockmarket.navigation.Routes.FUNDS_AND_PAYMENTS
import com.assignments.stockmarket.navigation.Routes.HELP_AND_SUPPORT
import com.assignments.stockmarket.navigation.Routes.HOLDINGS
import com.assignments.stockmarket.navigation.Routes.INVESTMENT_AMOUNT
import com.assignments.stockmarket.navigation.Routes.KYC
import com.assignments.stockmarket.navigation.Routes.LOANS
import com.assignments.stockmarket.navigation.Routes.LOGIN
import com.assignments.stockmarket.navigation.Routes.MPIN
import com.assignments.stockmarket.navigation.Routes.MPIN_FINGER_PRINT
import com.assignments.stockmarket.navigation.Routes.MPIN_RESET
import com.assignments.stockmarket.navigation.Routes.MPIN_VERIFY
import com.assignments.stockmarket.navigation.Routes.MUTUAL_FUNDS
import com.assignments.stockmarket.navigation.Routes.NOTIFICATIONS_SETTINGS
import com.assignments.stockmarket.navigation.Routes.ORDERS
import com.assignments.stockmarket.navigation.Routes.POSITIONS
import com.assignments.stockmarket.navigation.Routes.PROFILE
import com.assignments.stockmarket.navigation.Routes.REPORTS
import com.assignments.stockmarket.navigation.Routes.RESET_PASSWORD
import com.assignments.stockmarket.navigation.Routes.SEARCH
import com.assignments.stockmarket.navigation.Routes.SECURITY_AND_LOGIN
import com.assignments.stockmarket.navigation.Routes.SETTINGS
import com.assignments.stockmarket.navigation.Routes.SIGN_UP
import com.assignments.stockmarket.navigation.Routes.SPLASH
import com.assignments.stockmarket.navigation.Routes.UPI
import com.assignments.stockmarket.navigation.Routes.WELCOME
import com.assignments.stockmarket.navigation.Routes.REFER_AND_EARN
import com.assignments.stockmarket.notifications.NotificationSettingsScreen
import com.assignments.stockmarket.profile.ProfileScreen
import com.assignments.stockmarket.refer_and_earn.ReferAndEarnScreen
import com.assignments.stockmarket.reports_statements.ReportsScreen
import com.assignments.stockmarket.search.SearchScreen
import com.assignments.stockmarket.security.SecurityScreen
import com.assignments.stockmarket.settings.SettingsScreen
import com.assignments.stockmarket.tabs.explore.AllStocksScreen
import com.assignments.stockmarket.tabs.explore.ExploreScreen
import com.assignments.stockmarket.tabs.holdings.HoldingsScreen
import com.assignments.stockmarket.tabs.orders.OrdersScreen
import com.assignments.stockmarket.tabs.positions.PositionsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH
    ) {
        composable(SPLASH) {
            SplashScreen(navController)
        }

        composable(LOGIN) {
            LoginScreen(navController)
        }

        composable(FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController)
        }

        composable(SIGN_UP) {
            SignUpScreen(navController)
        }

        composable(MPIN) {
            MPINScreen(navController)
        }

        composable(MPIN_VERIFY) {
            MPINScreen(navController, isVerifyMode = true)
        }

        composable(MPIN_RESET) {
            MPINScreen(navController, isResetMode = true)
        }

        composable(WELCOME) {
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

        composable(MPIN_FINGER_PRINT) {
            MPINFingerPrintScreen(navController)
        }

        composable(
            route = RESET_PASSWORD,
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

        composable(DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(SEARCH) {
            SearchScreen(navController)
        }

        composable(FAndQ) {
            FAndQScreen(navController)
        }

        composable(MUTUAL_FUNDS) {
            MutualFundsScreen(navController)
        }

        composable(UPI) {
            UPIScreen(navController)
        }

        composable(LOANS) {
            LoansScreen(navController)
        }

        composable(
            route = BUY_STOCK
        ) { backStackEntry ->

            val stockId = backStackEntry.arguments?.getString("stockId")?.toInt() ?: 0

            BuyStockScreen(
                navController = navController,
                stockId = stockId
            )
        }

        composable(
            route = BUY_STOCK_NAME,
            arguments = listOf(
                navArgument("stockName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val stockName = backStackEntry.arguments?.getString("stockName") ?: ""
            BuyStockScreen(
                navController = navController,
                stockId = 0,
                stockName = stockName
            )
        }

        composable(ALL_STOCKS) {
            AllStocksScreen(navController)
        }

        composable(EXPLORE) {
            ExploreScreen(navController)
        }

        composable(HOLDINGS) {
            HoldingsScreen(navController)
        }

        composable(POSITIONS) {
            PositionsScreen(navController)
        }

        composable(ORDERS) {
            OrdersScreen(navController)
        }

        composable(INVESTMENT_AMOUNT) {
            InvestmentScreen(navController)
        }

        composable(PROFILE) {
            ProfileScreen(navController)
        }

        composable(SETTINGS) {
            SettingsScreen(navController)
        }

        composable(NOTIFICATIONS_SETTINGS) {
            NotificationSettingsScreen(navController)
        }

        composable(SECURITY_AND_LOGIN) {
            SecurityScreen(navController)
        }

        composable(CHANGE_PASSWORD) {
            ChangePasswordScreen(navController)
        }

        composable(ABOUT) {
            AboutScreen(navController)
        }

        composable(HELP_AND_SUPPORT) {
            HelpSupportScreen(navController)
        }

        composable(FUNDS_AND_PAYMENTS) {
            FundsPaymentsScreen(navController)
        }

        composable(REPORTS) {
            ReportsScreen(navController)
        }

        composable(ACTIVE_DEVICES) {
            ActiveDevicesScreen(navController)
        }

        composable(KYC) {
            KYCScreen(navController)
        }

        composable(REFER_AND_EARN) {
            ReferAndEarnScreen(navController)
        }

    }
}