package com.assignments.stockmarket

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
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
import com.assignments.stockmarket.reusables.OTPInput
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun OTPScreen(
    navController: NavController,
    email: String,
    expectedOtp: String,
    isResetMpin: Boolean = false,
    isForgotPassword: Boolean = false
) {
    OtpVerificationContent(navController, email, expectedOtp, isResetMpin, isForgotPassword)
}

@Composable
private fun OtpVerificationContent(
    navController: NavController,
    email: String,
    expectedOtp: String,
    isResetMpin: Boolean,
    isForgotPassword: Boolean
) {
    val otpLength = if (isForgotPassword) 6 else 4
    var currentExpectedOtp by remember { mutableStateOf(expectedOtp) }
    var enteredOtp by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var resendKey by remember { mutableIntStateOf(0) } // used to reset OTPInput on resend

    // 60-second countdown timer
    var timeLeft by remember { mutableIntStateOf(120) }
    var timerRunning by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()


    // Countdown timer effect — restarts when timerRunning flips to true
    LaunchedEffect(timerRunning, resendKey) {
        if (timerRunning) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            timerRunning = false
        }
    }

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

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.padding(top = 100.dp)
            )

            // OTP
            Text(
                text = stringResource(R.string.label_otp),
                color = Color.White,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                key(resendKey) {
                    OTPInput(
                        otpLength = otpLength,
                        onOtpComplete = { otp ->
                            enteredOtp = otp
                        },
                        onValueChange = { otp ->
                            enteredOtp = otp
                            otpError = null
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Timer display
                val seconds = timeLeft
                val formatted = String.format("%02d:%02d", seconds / 60, seconds % 60)
                Text(
                    text = "$formatted ${stringResource(R.string.label_seconds_left)}",
                    color = colorResource(R.color.text_primary),
                    fontSize = 15.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold
                )

                // Resend button — active only when timer reaches 0
                Text(
                    text = stringResource(R.string.action_resend_now),
                    color = if (!timerRunning)
                        colorResource(R.color.text_primary)
                    else
                        colorResource(R.color.text_primary).copy(alpha = 0.4f),
                    fontSize = 15.sp,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(enabled = !timerRunning && !isLoading) {
                        // Generate new OTP, call API, reset timer
                        isLoading = true
                        otpError = null
                        coroutineScope.launch {
                            if (isForgotPassword) {
                                // Resend via forgot password API
                                val isEmail = "@" in email
                                val result = if (isEmail) {
                                    forgotPasswordApi(email = email)
                                } else {
                                    forgotPasswordApi(phoneNumber = email)
                                }
                                isLoading = false
                                if (result.success && !result.otp.isNullOrEmpty()) {
                                    currentExpectedOtp = result.otp
                                    enteredOtp = ""
                                    resendKey++
                                    timeLeft = 120
                                    timerRunning = true
                                } else {
                                    otpError = result.message ?: "Failed to resend OTP. Try again."
                                }
                            } else {
                                val newOtp = (1000..9999).random().toString()
                                val sent = sendOtpApi(email, newOtp)
                                isLoading = false
                                if (sent) {
                                    currentExpectedOtp = newOtp
                                    enteredOtp = ""
                                    resendKey++
                                    timeLeft = 120
                                    timerRunning = true
                                } else {
                                    otpError = "Failed to resend OTP. Try again."
                                }
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.msg_otp_expiry),
                fontSize = 15.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.text_primary),
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Circular Arrow Button — verify OTP
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.bg_button_primary))
                    .border(2.dp, Color.White, CircleShape)
                    .clickable {
                        if (isLoading) return@clickable

                        if (enteredOtp.length < otpLength) {
                            otpError = "Please enter the complete $otpLength-digit OTP"
                            return@clickable
                        }

                        if (enteredOtp != currentExpectedOtp) {
                            otpError = "Invalid OTP. Please try again."
                            return@clickable
                        }

                        // OTP matched — handle based on mode
                        otpError = null
                        isLoading = true
                        coroutineScope.launch {
                            if (isForgotPassword) {
                                // Forgot password flow — navigate to reset password screen
                                isLoading = false
                                val encodedIdentifier = Uri.encode(email)
                                navController.navigate("reset_password/$encodedIdentifier") {
                                    popUpTo("otp/{email}/{otp}") { inclusive = true }
                                }
                            } else if (isResetMpin) {
                                // Reset MPIN flow — skip updateStatusApi, navigate to set new MPIN
                                isLoading = false
                                navController.navigate("mpin_reset") {
                                    popUpTo("otp/{email}/{otp}") { inclusive = true }
                                }
                            } else {
                                // Normal signup flow — call update status API, store email, navigate to MPIN
                                val (updated, _) = updateStatusApi(email)
                                isLoading = false
                                if (updated) {
                                    // Store email in Paper NoSQL DB
                                    withContext(Dispatchers.IO) {
                                        Paper.book().write("user_email", email)
                                    }
                                    navController.navigate("mpin") {
                                        popUpTo("otp/{email}/{otp}") { inclusive = true }
                                    }
                                } else {
                                    otpError = "OTP verification failed. Please try again."
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = stringResource(R.string.label_otp),
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Error message
            if (otpError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = otpError.orEmpty(),
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

