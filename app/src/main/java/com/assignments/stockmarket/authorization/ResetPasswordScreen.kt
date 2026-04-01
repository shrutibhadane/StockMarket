package com.assignments.stockmarket.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.navigation.Routes.LOGIN
import com.assignments.stockmarket.resetPasswordApi
import com.assignments.stockmarket.reusables.CustomTextField
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    identifier: String
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var apiError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFocused by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Password criteria checks
    val hasMinLength = password.length >= 8
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasLowerCase = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSymbol = password.any { !it.isLetterOrDigit() }
    val allCriteriaMet = hasMinLength && hasUpperCase && hasLowerCase && hasDigit && hasSymbol
    val showPasswordCriteria = isPasswordFocused && !isConfirmPasswordFocused && password.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_primary))
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.padding(top = 80.dp)
            )

            // Reset Password Title
            Text(
                text = stringResource(R.string.action_reset_password),
                style = AppTextStyles.bold(24),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Frozen Email / Phone field (read-only)
            CustomTextField(
                placeholder = stringResource(R.string.hint_email_or_phone),
                value = identifier,
                onValueChange = { /* No-op: field is frozen */ },
                readOnly = true,
                enabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            Box(
                modifier = Modifier.onFocusChanged {
                    isPasswordFocused = it.isFocused || it.hasFocus
                }
            ) {
                CustomTextField(
                    placeholder = stringResource(R.string.label_password),
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                        if (confirmPassword.isNotEmpty() && confirmPassword != it) {
                            confirmPasswordError = "Passwords do not match"
                        } else {
                            confirmPasswordError = null
                        }
                        apiError = null
                    },
                    isPassword = true,
                    errorMessage = passwordError
                )
            }

            // Password Criteria List
            if (showPasswordCriteria) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(start = 4.dp)) {
                    ResetPasswordCriteriaRow("At least 8 characters", hasMinLength)
                    ResetPasswordCriteriaRow("At least 1 uppercase letter", hasUpperCase)
                    ResetPasswordCriteriaRow("At least 1 lowercase letter", hasLowerCase)
                    ResetPasswordCriteriaRow("At least 1 digit", hasDigit)
                    ResetPasswordCriteriaRow("At least 1 symbol", hasSymbol)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            Box(
                modifier = Modifier.onFocusChanged {
                    isConfirmPasswordFocused = it.isFocused || it.hasFocus
                }
            ) {
                CustomTextField(
                    placeholder = stringResource(R.string.label_confirm_password),
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = when {
                            it.isEmpty() -> "Confirm Password should not be empty"
                            password != it -> "Passwords do not match"
                            else -> null
                        }
                        apiError = null
                    },
                    isPassword = true,
                    errorMessage = confirmPasswordError
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Circular Arrow Button with Loader
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(
                        if (allCriteriaMet)
                            colorResource(R.color.bg_button_primary)
                        else
                            colorResource(R.color.bg_button_primary).copy(alpha = 0.4f)
                    )
                    .border(2.dp, colorResource(R.color.white), CircleShape)
                    .clickable {
                        if (isLoading) return@clickable
                        if (!allCriteriaMet) return@clickable

                        // Local validation
                        var valid = true

                        if (password.isEmpty()) {
                            passwordError = "Password should not be empty"; valid = false
                        }

                        if (confirmPassword.isEmpty()) {
                            confirmPasswordError = "Confirm Password should not be empty"; valid = false
                        } else if (password != confirmPassword) {
                            confirmPasswordError = "Passwords do not match"; valid = false
                        }

                        if (!valid) return@clickable

                        apiError = null
                        isLoading = true

                        val isEmail = "@" in identifier

                        coroutineScope.launch {
                            val result = if (isEmail) {
                                resetPasswordApi(
                                    email = identifier,
                                    password = password.trim()
                                )
                            } else {
                                resetPasswordApi(
                                    phoneNumber = identifier,
                                    password = password.trim()
                                )
                            }
                            isLoading = false

                            if (result.success) {
                                showSuccessDialog = true
                            } else {
                                apiError = result.message ?: "Failed to reset password. Please try again."
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = colorResource(R.color.white),
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = stringResource(R.string.action_reset_password),
                        tint = if (allCriteriaMet) colorResource(R.color.white) else colorResource(R.color.white).copy(alpha = 0.4f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // API Error Message
            if (apiError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = apiError.orEmpty(),
                    style = AppTextStyles.regular(14, colorResource(R.color.text_error)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Success Dialog — navigate to login screen on Ok
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { /* Prevent dismiss by tapping outside */ },
            title = {
                Text(
                    text = "Password Reset Successful! 🎉",
                    style = AppTextStyles.bold(20, colorResource(R.color.text_success_light)),
                    )
            },
            text = {
                Text(
                    text = "Your password has been updated successfully. Please login with your new password.",
                    style = AppTextStyles.regular(14),
                    )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate(LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        text = "Ok",
                        style = AppTextStyles.bold(16),
                        )
                }
            }
        )
    }
}

@Composable
private fun ResetPasswordCriteriaRow(label: String, met: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Icon(
            imageVector = if (met) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = if (met) Color.Green else Color.Red,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = AppTextStyles.regular(12),
            color = if (met) Color.Green else Color.Red,
        )
    }
}

