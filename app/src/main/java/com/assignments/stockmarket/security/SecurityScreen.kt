package com.assignments.stockmarket.security

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.navigation.Routes.ACTIVE_DEVICES
import com.assignments.stockmarket.navigation.Routes.CHANGE_PASSWORD
import com.assignments.stockmarket.navigation.Routes.LOGIN
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.dialogs.LogoutDialog
import com.assignments.stockmarket.security.components.BiometricSwitchItem
import com.assignments.stockmarket.security.components.SecurityItem
import com.assignments.stockmarket.security.components.SecuritySectionTitle

@Composable
fun SecurityScreen(navController: NavController) {

    var showLogoutAllDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarBackArrow(navController, title = stringResource(R.string.section_security))
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
        ) {

            item { SecuritySectionTitle(stringResource(R.string.label_authentication)) }

            item {
                SecurityItem(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.action_change_password),
                    onClick = {
                        navController.navigate(CHANGE_PASSWORD)
                    }
                )
            }

            item {
                BiometricSwitchItem(stringResource(R.string.action_enable_fingerprint))
            }

            item { SecuritySectionTitle(stringResource(R.string.label_session)) }

            item {
                SecurityItem(
                    icon = Icons.Default.Devices,
                    title = stringResource(R.string.label_active_devices),
                    onClick = { navController.navigate(ACTIVE_DEVICES)}
                )
            }

            item {
                SecurityItem(
                    icon = Icons.Default.Logout,
                    title = stringResource(R.string.action_logout_all_devices),
                    onClick = {
                        showLogoutAllDialog = true
                    }
                )
            }

        }
    }

    if (showLogoutAllDialog) {
        LogoutDialog(
            showDialog = true,
            message = stringResource(R.string.msg_logout_all_devices),
            onConfirm = {
                showLogoutAllDialog = false

                // 👉 Call logout logic
                Toast.makeText(
                    context,
                    context.getString(R.string.logged_out_from_all_devices),
                    Toast.LENGTH_SHORT
                ).show()

                // 👉 Navigate to login screen
                navController.navigate(LOGIN) {
                    popUpTo(0)
                }
            },
            onDismiss = {
                showLogoutAllDialog = false
            }
        )
    }
}
