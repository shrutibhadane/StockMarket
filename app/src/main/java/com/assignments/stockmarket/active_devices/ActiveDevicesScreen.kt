package com.assignments.stockmarket.active_devices


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.active_devices.components.DeviceItem
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
@Composable
fun ActiveDevicesScreen(navController: NavController) {

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController = navController,
                title = stringResource(R.string.label_active_devices)
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { Spacer(modifier = Modifier.height(10.dp)) }

            items(devices) { device ->
                DeviceItem(device = device)
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}