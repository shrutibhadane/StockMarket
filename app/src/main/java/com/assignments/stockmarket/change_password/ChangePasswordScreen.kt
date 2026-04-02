package com.assignments.stockmarket.change_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.change_password.components.PasswordField
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles


@Composable
fun ChangePasswordScreen(navController: NavController) {

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController = navController,
                title = stringResource(R.string.action_change_password)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            PasswordField(
                label = stringResource(R.string.label_current_password),
                value = currentPassword,
                onValueChange = { currentPassword = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                label = stringResource(R.string.label_new_password),
                value = newPassword,
                onValueChange = { newPassword = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                label = stringResource(R.string.label_confirm_password),
                value = confirmPassword,
                onValueChange = { confirmPassword = it }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                        when {
                            currentPassword.isEmpty() ||
                                    newPassword.isEmpty() ||
                                    confirmPassword.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_fill_all_fields),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            newPassword.length < 8 -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_password_length),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            newPassword != confirmPassword -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_password_mismatch),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            newPassword == currentPassword -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_password_same),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                // Call API or ViewModel
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.msg_password_updated),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.bg_button_secondary_light)
                )
            ) {
                Text(
                    text = stringResource(R.string.action_update_password),
                    style = AppTextStyles.bold(14, colorResource(R.color.bg_primary)),
                    )
            }
        }
    }
}