package com.assignments.stockmarket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import io.paperdb.Paper
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject

private const val CHECK_STATUS_URL = "https://system-project-api.onrender.com/api/checkthestatus"

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds splash display

        // Check if email is stored in Paper NoSQL DB
        val storedEmail: String? = withContext(Dispatchers.IO) {
            Paper.book().read<String>("user_email")
        }

        if (storedEmail.isNullOrEmpty()) {
            // No stored email → go to login
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            // Email exists → call checkthestatus API
            val status = checkTheStatusApi(storedEmail)

            if (status.emailVerified) {
                // Check if MPIN is stored locally
                val storedMpin: String? = withContext(Dispatchers.IO) {
                    Paper.book().read<String>("user_mpin")
                }

                if (!storedMpin.isNullOrEmpty()) {
                    // email_verified = true & MPIN available → go to dashboard
                    navController.navigate("mpin_finger_print") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    // email_verified = true but no MPIN → go to MPIN screen
                    navController.navigate("mpin") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            } else {
                // email_verified = false → go to login
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.screen_background)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(300.dp)
            )

            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

private data class StatusResult(
    val emailVerified: Boolean = false,
    val phoneVerified: Boolean = false
)

/** Call checkthestatus API to get email_verified and phone_verified status. */
private suspend fun checkTheStatusApi(email: String): StatusResult = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(CHECK_STATUS_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 15_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }
        val payload = JSONObject()
            .put("email", email)
            .toString()
        OutputStreamWriter(connection.outputStream).use { it.write(payload); it.flush() }

        if (connection.responseCode !in 200..299) {
            return@withContext StatusResult()
        }

        val body = BufferedReader(connection.inputStream.reader()).use { it.readText() }
        val json = JSONObject(body)
        StatusResult(
            emailVerified = json.optBoolean("email_verified", false),
            phoneVerified = json.optBoolean("phone_verified", false)
        )
    } catch (_: Exception) {
        StatusResult()
    } finally {
        connection?.disconnect()
    }
}
