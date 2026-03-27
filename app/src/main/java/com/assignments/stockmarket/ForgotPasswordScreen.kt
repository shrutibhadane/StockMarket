package com.assignments.stockmarket

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.reusables.CustomTextField
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    onSubmitClick: () -> Unit = {}
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }
    var apiError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_primary))
            .padding(horizontal = 24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔹 Logo
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(top = 100.dp)
            )

            Text(
                text = stringResource(R.string.forgot_password),
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))

            // 🔹 Email / Phone Input
            CustomTextField(
                placeholder = stringResource(R.string.enter_your_email_phone),
                value = emailOrPhone,
                onValueChange = {
                    emailOrPhone = it
                    if (it.isNotEmpty()) inputError = null
                    apiError = null
                },
                errorMessage = inputError
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Circular Arrow Button with loading animation
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.button_background_color))
                    .border(2.dp, colorResource(R.color.white), CircleShape)
                    .clickable {
                        if (isLoading) return@clickable

                        // Validation
                        if (emailOrPhone.trim().isEmpty()) {
                            inputError = "Email or phone number should not be empty"
                            return@clickable
                        }

                        inputError = null
                        apiError = null
                        isLoading = true

                        val trimmed = emailOrPhone.trim()
                        val isEmail = "@" in trimmed

                        coroutineScope.launch {
                            val result = if (isEmail) {
                                forgotPasswordApi(email = trimmed)
                            } else {
                                forgotPasswordApi(phoneNumber = trimmed)
                            }

                            isLoading = false

                            if (result.success && !result.otp.isNullOrEmpty()) {
                                onSubmitClick()
                                val encodedIdentifier = Uri.encode(trimmed)
                                navController.navigate(
                                    "otp/$encodedIdentifier/${result.otp}?isForgotPassword=true"
                                ) {
                                    popUpTo("forgot_password") { inclusive = true }
                                }
                            } else {
                                apiError = result.message
                                    ?: "Something went wrong. Please try again."
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
                        contentDescription = stringResource(R.string.forget_password),
                        tint = colorResource(R.color.white),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // 🔹 API Error Message
            if (apiError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = apiError.orEmpty(),
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}