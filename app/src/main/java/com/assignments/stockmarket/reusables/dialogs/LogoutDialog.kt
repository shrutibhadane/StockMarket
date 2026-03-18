package com.assignments.stockmarket.reusables.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun LogoutDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    if (showDialog) {

        AlertDialog(
            onDismissRequest = { onDismiss() },

            containerColor = colorResource(R.color.screen_background),

            shape = RoundedCornerShape(12.dp),

            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(12.dp)
                ),

            title = {
                Column {
                    Text(
                        text = stringResource(R.string.logout),
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(R.color.white),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(
                        color = colorResource(R.color.light_blue_button_bg_color).copy(alpha = 0.3f)
                    )
                }
            },


            text = {
                Text(
                    text = stringResource(R.string.do_you_want_to_logout_from_your_account_you_will_need_to_enter_your_mpin_to_login_again),
                    fontSize = 14.sp,
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily
                )
            },

            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(
                        text = "Yes",
                        fontSize = 14.sp,
                        color = colorResource(R.color.white),
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily
                    )
                }
            },

            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(
                        text = "No",
                        color = colorResource(R.color.light_blue_button_bg_color),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily
                    )
                }
            }
        )
    }
}