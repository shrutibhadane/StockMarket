package com.assignments.stockmarket.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.dialogs.LogoutDialog
import com.assignments.stockmarket.settings.components.SettingsItem
import com.assignments.stockmarket.settings.components.SettingsSectionTitle

@Composable
fun SettingsScreen(navController: NavController) {

    var showLogoutDialog by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                AppBarBackArrow(
                    navController = navController,
                    title = stringResource(R.string.settings)
                )
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.screen_background))
                    .padding(innerPadding)
            ) {

                item { SettingsSectionTitle(stringResource(R.string.general)) }

                item {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = stringResource(R.string.profile),
                        onClick = {
                            navController.navigate("profile")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = stringResource(R.string.notifications),
                        onClick = {
                            navController.navigate("notification_settings")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Security,
                        title = stringResource(R.string.security_login),
                        onClick = {
                            navController.navigate("security_and_login")
                        }
                    )
                }

                item { SettingsSectionTitle(stringResource(R.string.trading)) }

                item {
                    SettingsItem(
                        icon = Icons.Default.AccountBalanceWallet,
                        title = stringResource(R.string.funds_payments),
                        onClick = {
                            navController.navigate("funds_payments")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = stringResource(R.string.reports_statements),
                        onClick = {
                            navController.navigate("reports")
                        }
                    )
                }

                item { SettingsSectionTitle(stringResource(R.string.app)) }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = stringResource(R.string.about),
                        onClick = {
                            navController.navigate("about")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = stringResource(R.string.help_support),
                        onClick = {
                            navController.navigate("help_support")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Logout,
                        title = stringResource(R.string.logout),
                        onClick = { showLogoutDialog = true }
                    )
                }

            }
        }

    LogoutDialog(
        showDialog = showLogoutDialog,
        message = stringResource(R.string.do_you_want_to_logout_from_your_account_you_will_need_to_enter_your_mpin_to_login_again),
        onConfirm = {

            showLogoutDialog = false

            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )

}

