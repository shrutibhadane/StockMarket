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
                    title = "Settings"
                )
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.screen_background))
                    .padding(innerPadding)
            ) {

                item { SettingsSectionTitle("General") }

                item {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Profile",
                        onClick = {
                            navController.navigate("profile")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        onClick = {
                            navController.navigate("notification_settings")
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Security,
                        title = "Security & Login",
                        onClick = {
                            navController.navigate("security_and_login")
                        }
                    )
                }

                item { SettingsSectionTitle("Trading") }

                item {
                    SettingsItem(
                        icon = Icons.Default.AccountBalanceWallet,
                        title = "Funds & Payments",
                        onClick = { }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = "Reports & Statements",
                        onClick = { }
                    )
                }

                item { SettingsSectionTitle("App") }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = "About",
                        onClick = { }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = "Help & Support",
                        onClick = { }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Logout,
                        title = "Logout",
                        onClick = { showLogoutDialog = true }
                    )
                }

            }
        }

    LogoutDialog(
        showDialog = showLogoutDialog,
        onConfirm = {

            showLogoutDialog = false

            navController.navigate("mpin") {
                popUpTo(0) { inclusive = true }
            }
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )

}

