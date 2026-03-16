package com.assignments.stockmarket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MPINScreen (
    navController: NavController,
    isVerifyMode: Boolean = false,
    isResetMode: Boolean = false,
    onMPINClick: () -> Unit = {}
) {
    var enteredMpin by remember { mutableStateOf("") }
    var mpinError by remember { mutableStateOf<String?>(null) }
    var wrongAttempts by remember { mutableIntStateOf(0) }
    var isSuspended by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.screen_background)) // Background color
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

            // 🔹 MPIN title — changes based on mode
            Text(
                text = when {
                    isResetMode -> "Set New MPIN"
                    isVerifyMode -> "Enter MPIN"
                    else -> stringResource(R.string.mpin)
                },
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            // 🔹 Subtitle for verify mode
            if (isVerifyMode) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your session has expired. Please re-enter your MPIN to continue.",
                    color = colorResource(R.color.text_color),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            if (isSuspended) {
                // 🔹 Suspended state — hide input boxes, show message + reset button
                Text(
                    text = "Due to multiple incorrect MPIN's, Your MPIN is suspended.",
                    color = Color(0xFFFF6B6B),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (isLoading) return@Button
                        isLoading = true
                        coroutineScope.launch {
                            val email: String? = withContext(Dispatchers.IO) {
                                Paper.book().read<String>("user_email")
                            }
                            if (email.isNullOrBlank()) {
                                mpinError = "No registered email found. Please contact support."
                                isLoading = false
                                return@launch
                            }
                            val otp = (1000..9999).random().toString()
                            val sent = sendOtpApi(email, otp)
                            isLoading = false
                            if (sent) {
                                navController.navigate("otp/$email/$otp?isResetMpin=true")
                            } else {
                                mpinError = "Failed to send OTP. Please try again."
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.button_background_color)
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Reset MPIN",
                            color = Color.White,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                // 🔹 Error message for suspended state
                if (mpinError != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = mpinError.orEmpty(),
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontFamily = PoppinsFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            } else {
                // 🔹 Normal state — show OTP input + arrow button

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OTPInput(
                    onOtpComplete = { otp ->
                        enteredMpin = otp
                    },
                    onValueChange = { otp ->
                        enteredMpin = otp
                        mpinError = null
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))


            // 🔹 Circular Arrow Button
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.button_background_color))
                    .border(2.dp, colorResource(R.color.white), CircleShape)
                    .clickable {
                        if (enteredMpin.length < 4) {
                            mpinError = "Please enter a 4-digit MPIN"
                            return@clickable
                        }
                        mpinError = null

                        if (isVerifyMode) {
                            // ── Verify mode: compare with stored MPIN ──
                            coroutineScope.launch {
                                val storedMpin: String? = withContext(Dispatchers.IO) {
                                    Paper.book().read<String>("user_mpin")
                                }
                                if (enteredMpin == storedMpin) {
                                    // Correct → save last login time and go to dashboard
                                    withContext(Dispatchers.IO) {
                                        Paper.book().write("last_login_time", System.currentTimeMillis())
                                    }
                                    navController.navigate("dashboard") {
                                        popUpTo("mpin_verify") { inclusive = true }
                                    }
                                } else {
                                    wrongAttempts++
                                    if (wrongAttempts >= 3) {
                                        isSuspended = true
                                        mpinError = null
                                    } else {
                                        mpinError = "Invalid MPIN. Please try again. (${3 - wrongAttempts} attempts left)"
                                    }
                                }
                            }
                        } else if (isResetMode) {
                            // ── Reset mode: save new MPIN (replace old) and go to dashboard ──
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    Paper.book().write("user_mpin", enteredMpin)
                                    Paper.book().write("last_login_time", System.currentTimeMillis())
                                }
                                navController.navigate("dashboard") {
                                    popUpTo("mpin_reset") { inclusive = true }
                                }
                            }
                        } else {
                            // ── Setup mode: save new MPIN and go to welcome ──
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    Paper.book().write("user_mpin", enteredMpin)
                                    Paper.book().write("last_login_time", System.currentTimeMillis())
                                }
                                navController.navigate("welcome") {
                                    popUpTo("mpin") { inclusive = true }
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.mpin),
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(32.dp)
                )
            }

            // 🔹 Error message
            if (mpinError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mpinError.orEmpty(),
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            } // end of !isSuspended else block

            Spacer(modifier = Modifier.weight(1f))

            // 🔹 Finger print Button
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { onMPINClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_biometric),
                    contentDescription = "MPIN",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Click here to enable biometric",
                color = colorResource(R.color.white),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clickable {
                        navController.navigate("mpin_finger_print")
                    }
            )

        }
    }
}