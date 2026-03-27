package com.assignments.stockmarket.active_devices.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.active_devices.Device
import com.assignments.stockmarket.reusables.dialogs.LogoutDialog
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun DeviceItem(device: Device) {

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutDialog(
            showDialog = true,
            message = stringResource(R.string.do_you_want_to_logout_from_your_account),
            onConfirm = {
                showLogoutDialog = false
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.bg_button_secondary_light)
        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = device.name,
                        fontSize = 14.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = device.location,
                        fontSize = 12.sp,
                        fontFamily = PoppinsFamily,
                        color = colorResource(R.color.white)
                    )

                    Text(
                        text = device.lastActive,
                        fontSize = 12.sp,
                        fontFamily = PoppinsFamily,
                        color = colorResource(R.color.bg_text_field)
                    )
                }

                if (device.isCurrent) {
                    Text(
                        text = "This Device",
                        color = colorResource(R.color.bg_button_secondary_light),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (!device.isCurrent) {
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = {  showLogoutDialog = true },
                        modifier = Modifier.size(width = 120.dp, height = 35.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.bg_button_secondary_dark)
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.logout),
                            fontFamily = PoppinsFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white)
                        )
                    }
                }
            }
        }
    }
}

