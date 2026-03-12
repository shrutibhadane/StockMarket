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
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginClick: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var authError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val invalidCredentialsMessage = stringResource(R.string.invalid_userid_or_password)
    val otpSentFailedMessage = stringResource(R.string.otp_sent_failed)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.screen_background))
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

            // 🔹 Login Title
            Text(
                text = stringResource(R.string.login),
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))

            // 🔹 Username Field
            CustomTextField(
                placeholder = stringResource(R.string.username),
                value = username,
                onValueChange = {
                    username = it
                    if (it.isNotEmpty()) usernameError = null
                    authError = null
                },
                errorMessage = usernameError
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Password Field
            CustomTextField(
                placeholder = stringResource(R.string.password),
                value = password,
                onValueChange = {
                    password = it
                    if (it.isNotEmpty()) passwordError = null
                    authError = null
                },
                isPassword = true,
                errorMessage = passwordError
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🔹 Forget Password
            Text(
                text = stringResource(R.string.forget_password),
                color = colorResource(R.color.text_color),
                fontSize = 15.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.navigate("dashboard")
                    }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Circular Arrow Button
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.button_background_color))
                    .border(2.dp, colorResource(R.color.white), CircleShape)
                    .clickable {
                        if (isLoading) return@clickable

                        var valid = true
                        if (username.isEmpty()) {
                            usernameError = "Username should not be empty"
                            valid = false
                        }
                        if (password.isEmpty()) {
                            passwordError = "Password should not be empty"
                            valid = false
                        }

                        if (!valid) return@clickable

                         authError = null
                         isLoading = true
                         coroutineScope.launch {
                             val success = authenticateUser(username.trim(), password.trim())
                             if (success) {
                                // Generate a 4-digit OTP and send it
                                val otp = (1000..9999).random().toString()
                                val otpSent = sendOtpApi(username.trim(), otp)
                                isLoading = false
                                if (otpSent) {
                                    onLoginClick()
                                    val encodedEmail = Uri.encode(username.trim())
                                    navController.navigate("otp/$encodedEmail/$otp") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                } else {
                                    authError = otpSentFailedMessage
                                }
                            } else {
                                isLoading = false
                                 authError = invalidCredentialsMessage
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
                        contentDescription = stringResource(R.string.login),
                        tint = colorResource(R.color.white),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            if (authError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = authError.orEmpty(),
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔹 Bottom Text
            Text(
                text = stringResource(R.string.don_t_have_any_account_click_to_create),
                color = colorResource(R.color.white),
                fontSize = 12.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable {
                        navController.navigate("sign_up")
                    }
            )
        }
    }
}

private suspend fun authenticateUser(username: String, password: String): Boolean = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null

    try {
        connection = (URL(LOGIN_API_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 15_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }

        val payload = JSONObject()
            .put("user_id", username)
            .put("password", password)
            .toString()

        OutputStreamWriter(connection.outputStream).use { writer ->
            writer.write(payload)
            writer.flush()
        }

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            return@withContext false
        }

        val body = BufferedReader(connection.inputStream.reader()).use { it.readText() }
        val json = JSONObject(body)
        json.optString("status").equals("OK", ignoreCase = true)
    } catch (_: Exception) {
        false
    } finally {
        connection?.disconnect()
    }
}

/** Send a 4-digit OTP to the server for delivery to the user. */
private suspend fun sendOtpApi(email: String, otp: String): Boolean = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(SEND_OTP_API_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 15_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }
        val payload = JSONObject()
            .put("email", email)
            .put("otp", otp)
            .toString()
        OutputStreamWriter(connection.outputStream).use { it.write(payload); it.flush() }
        connection.responseCode in 200..299
    } catch (_: Exception) {
        false
    } finally {
        connection?.disconnect()
    }
}

private const val LOGIN_API_URL = "https://system-project-api.onrender.com/api/login"
private const val SEND_OTP_API_URL = "https://system-project-api.onrender.com/api/sendotp"
