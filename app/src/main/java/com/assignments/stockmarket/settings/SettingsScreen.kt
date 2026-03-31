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
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.db.CompanyRepository
import com.assignments.stockmarket.navigation.Routes.ABOUT
import com.assignments.stockmarket.navigation.Routes.FUNDS_AND_PAYMENTS
import com.assignments.stockmarket.navigation.Routes.LOGIN
import com.assignments.stockmarket.navigation.Routes.NOTIFICATIONS_SETTINGS
import com.assignments.stockmarket.navigation.Routes.PROFILE
import com.assignments.stockmarket.navigation.Routes.REPORTS
import com.assignments.stockmarket.navigation.Routes.SECURITY_AND_LOGIN
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.dialogs.LogoutDialog
import com.assignments.stockmarket.settings.components.SettingsItem
import com.assignments.stockmarket.settings.components.SettingsSectionTitle
import io.paperdb.Paper
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {

    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                AppBarBackArrow(
                    navController = navController,
                    title = stringResource(R.string.screen_settings)
                )
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.bg_primary))
                    .padding(innerPadding)
            ) {

                item { SettingsSectionTitle(stringResource(R.string.section_general)) }

                item {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = stringResource(R.string.section_profile),
                        onClick = {
                            navController.navigate(PROFILE)
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = stringResource(R.string.section_notifications),
                        onClick = {
                            navController.navigate(NOTIFICATIONS_SETTINGS)
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Security,
                        title = stringResource(R.string.section_security),
                        onClick = {
                            navController.navigate(SECURITY_AND_LOGIN)
                        }
                    )
                }

                item { SettingsSectionTitle(stringResource(R.string.section_trading)) }

                item {
                    SettingsItem(
                        icon = Icons.Default.AccountBalanceWallet,
                        title = stringResource(R.string.section_funds),
                        onClick = {
                            navController.navigate(FUNDS_AND_PAYMENTS)
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Report,
                        title = stringResource(R.string.section_reports),
                        onClick = {
                            navController.navigate(REPORTS)
                        }
                    )
                }

                item { SettingsSectionTitle(stringResource(R.string.section_app)) }

                item {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = stringResource(R.string.section_about),
                        onClick = {
                            navController.navigate(ABOUT)
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Logout,
                        title = stringResource(R.string.action_logout),
                        onClick = { showLogoutDialog = true }
                    )
                }

            }
        }

    LogoutDialog(
        showDialog = showLogoutDialog,
        message = stringResource(R.string.msg_logout_confirm_mpin_login),
        onConfirm = {
            showLogoutDialog = false

            // Clear Room database (companies cache)
            coroutineScope.launch {
                CompanyRepository.clearAll(context)
            }

            // Clear Paper credentials
            Paper.book().destroy()

            navController.navigate(LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )

}

