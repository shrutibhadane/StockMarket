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
            AppBarBackArrow(navController, title = stringResource(R.string.security_login))
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
        ) {

            item { SecuritySectionTitle(stringResource(R.string.authentication)) }

            item {
                SecurityItem(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.change_password),
                    onClick = {
                        navController.navigate("change_password")
                    }
                )
            }

            item {
                BiometricSwitchItem(stringResource(R.string.enable_fingerprint_login))
            }

            item { SecuritySectionTitle(stringResource(R.string.session)) }

            item {
                SecurityItem(
                    icon = Icons.Default.Devices,
                    title = stringResource(R.string.active_devices),
                    onClick = { navController.navigate("active_devices") }
                )
            }

            item {
                SecurityItem(
                    icon = Icons.Default.Logout,
                    title = stringResource(R.string.logout_from_all_devices),
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
            message = stringResource(R.string.do_you_want_to_logout_from_all_devices),
            onConfirm = {
                showLogoutAllDialog = false

                // 👉 Call logout logic
                Toast.makeText(
                    context,
                    "Logged out from all devices",
                    Toast.LENGTH_SHORT
                ).show()

                // 👉 Navigate to login screen
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
            onDismiss = {
                showLogoutAllDialog = false
            }
        )
    }
}
