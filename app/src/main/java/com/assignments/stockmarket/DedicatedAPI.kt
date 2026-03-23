package com.assignments.stockmarket

import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONArray
import com.assignments.stockmarket.db.CompanyEntity

// ──────────────────────────────────────────────
//  Base URL
// ──────────────────────────────────────────────
private const val BASE_URL = "https://system-project-api.onrender.com/api"

// ──────────────────────────────────────────────
//  API Endpoints
// ──────────────────────────────────────────────
const val LOGIN_API_URL = "$BASE_URL/login"
const val SIGNUP_API_URL = "$BASE_URL/signup"
const val SEND_OTP_API_URL = "$BASE_URL/sendotp"
const val UPDATE_STATUS_API_URL = "$BASE_URL/updatestatus"
const val CHECK_STATUS_API_URL = "$BASE_URL/checkthestatus"
const val FORGOT_PASSWORD_API_URL = "$BASE_URL/forgotpassword"
const val RESET_PASSWORD_API_URL = "$BASE_URL/resetpassword"
const val COMPANIES_API_URL = "$BASE_URL/companies"

// ──────────────────────────────────────────────
//  Data classes
// ──────────────────────────────────────────────
data class SignUpResult(val success: Boolean, val message: String? = null)

data class StatusResult(
    val emailVerified: Boolean = false,
    val phoneVerified: Boolean = false
)

data class ForgotPasswordResult(
    val success: Boolean,
    val otp: String? = null,
    val message: String? = null
)

// ──────────────────────────────────────────────
//  API Functions
// ──────────────────────────────────────────────

/**
 * Authenticate a user via POST to /api/login.
 * Sends `user_id` and `password`.
 * Returns `true` if the server responds with status "OK".
 */
suspend fun authenticateUser(username: String, password: String): Boolean = withContext(Dispatchers.IO) {
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

/**
 * Register a new user via POST to /api/signup.
 * Sends first_name, last_name, username, email, phone_number, password.
 * Returns [SignUpResult] with success flag and optional error message.
 */
suspend fun registerUser(
    firstName: String,
    lastName: String,
    username: String,
    email: String,
    phoneNumber: String,
    password: String
): SignUpResult = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(SIGNUP_API_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 15_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }

        val payload = JSONObject()
            .put("first_name", firstName)
            .put("last_name", lastName)
            .put("username", username)
            .put("email", email)
            .put("phone_number", phoneNumber)
            .put("password", password)
            .toString()

        OutputStreamWriter(connection.outputStream).use { it.write(payload); it.flush() }

        val responseCode = connection.responseCode
        val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
        val body = BufferedReader(stream.reader()).use { it.readText() }
        val json = JSONObject(body)

        if (json.optString("status").equals("OK", ignoreCase = true)) {
            SignUpResult(success = true)
        } else {
            SignUpResult(success = false, message = json.optString("message").ifEmpty { null })
        }
    } catch (e: Exception) {
        SignUpResult(success = false, message = "Error: ${e.javaClass.simpleName}: ${e.message}")
    } finally {
        connection?.disconnect()
    }
}

/**
 * Send a 4-digit OTP via POST to /api/sendotp.
 * Sends `email` and `otp`.
 * Returns `true` if the server responds with 2xx status.
 */
suspend fun sendOtpApi(email: String, otp: String): Boolean = withContext(Dispatchers.IO) {
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

/**
 * Call updatestatus API via POST to /api/updatestatus.
 * Sends `email` to mark email_verified and phone_verified.
 * Returns a [Pair] of (success, responseMessage) for debugging.
 */
suspend fun updateStatusApi(email: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(UPDATE_STATUS_API_URL).openConnection() as HttpURLConnection).apply {
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
        val code = connection.responseCode
        val body = try {
            if (code in 200..299) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "No error body"
            }
        } catch (_: Exception) {
            "Could not read body"
        }
        Pair(code in 200..299, "HTTP $code: $body")
    } catch (e: Exception) {
        Pair(false, "Exception: ${e.message}")
    } finally {
        connection?.disconnect()
    }
}

/**
 * Call checkthestatus API via POST to /api/checkthestatus.
 * Sends `email` to get email_verified and phone_verified status.
 * Returns [StatusResult] with the verification flags.
 */
suspend fun checkTheStatusApi(email: String): StatusResult = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(CHECK_STATUS_API_URL).openConnection() as HttpURLConnection).apply {
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

/**
 * Call forgot password API via POST to /api/forgotpassword.
 * Sends `email` or `phone_number` to initiate password reset.
 * Returns [ForgotPasswordResult] with success flag, OTP (on success), and message.
 */
suspend fun forgotPasswordApi(
    email: String? = null,
    phoneNumber: String? = null
): ForgotPasswordResult = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(FORGOT_PASSWORD_API_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 15_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }

        val payload = JSONObject().apply {
            if (!email.isNullOrBlank()) put("email", email)
            if (!phoneNumber.isNullOrBlank()) put("phone_number", phoneNumber)
        }.toString()

        OutputStreamWriter(connection.outputStream).use { it.write(payload); it.flush() }

        val responseCode = connection.responseCode
        val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
        val body = BufferedReader(stream.reader()).use { it.readText() }
        val json = JSONObject(body)

        if (json.optString("status").equals("OK", ignoreCase = true)) {
            ForgotPasswordResult(
                success = true,
                otp = json.optString("otp").ifEmpty { null },
                message = json.optString("message").ifEmpty { null }
            )
        } else {
            ForgotPasswordResult(
                success = false,
                message = json.optString("message").ifEmpty { "Request failed" }
            )
        }
    } catch (e: Exception) {
        ForgotPasswordResult(success = false, message = "Error: ${e.javaClass.simpleName}: ${e.message}")
    } finally {
        connection?.disconnect()
    }
}

/**
 * Call reset password API via POST to /api/resetpassword.
 * Sends `email` or `phone_number` along with new `password`.
 * Returns [SignUpResult] with success flag and optional message.
 */
suspend fun resetPasswordApi(
    email: String? = null,
    phoneNumber: String? = null,
    password: String
): SignUpResult = withContext(Dispatchers.IO) {
    var connection: HttpURLConnection? = null
    try {
        connection = (URL(RESET_PASSWORD_API_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 15_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }

        val payload = JSONObject().apply {
            if (!email.isNullOrBlank()) put("email", email)
            if (!phoneNumber.isNullOrBlank()) put("phone_number", phoneNumber)
            put("password", password)
        }.toString()

        OutputStreamWriter(connection.outputStream).use { it.write(payload); it.flush() }

        val responseCode = connection.responseCode
        val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
        val body = BufferedReader(stream.reader()).use { it.readText() }
        val json = JSONObject(body)

        if (json.optString("status").equals("OK", ignoreCase = true)) {
            SignUpResult(success = true, message = json.optString("message").ifEmpty { null })
        } else {
            SignUpResult(success = false, message = json.optString("message").ifEmpty { "Request failed" })
        }
    } catch (e: Exception) {
        SignUpResult(success = false, message = "Error: ${e.javaClass.simpleName}: ${e.message}")
    } finally {
        connection?.disconnect()
    }
}

/**
 * Fetch companies list via GET from /api/companies.
 * Retries up to 3 times to handle Render free-tier cold starts.
 * Returns a list of [CompanyEntity] parsed from the JSON response.
 */
suspend fun fetchCompaniesApi(): List<CompanyEntity> = withContext(Dispatchers.IO) {
    val tag = "FetchCompaniesAPI"
    val maxRetries = 3

    for (attempt in 1..maxRetries) {
        var connection: HttpURLConnection? = null
        try {
            android.util.Log.i(tag, "Attempt $attempt/$maxRetries — GET $COMPANIES_API_URL")
            connection = (URL(COMPANIES_API_URL).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 60_000
                readTimeout = 60_000
                setRequestProperty("Accept", "application/json")
            }

            val responseCode = connection.responseCode
            android.util.Log.i(tag, "Attempt $attempt — Response code: $responseCode")

            if (responseCode != HttpURLConnection.HTTP_OK) {
                val errorBody = try {
                    connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "No error body"
                } catch (_: Exception) { "Could not read error body" }
                android.util.Log.e(tag, "Attempt $attempt — Non-200: $responseCode — $errorBody")
                connection.disconnect()
                if (attempt < maxRetries) {
                    Thread.sleep(2_000L)
                    continue
                }
                return@withContext emptyList()
            }

            val body = BufferedReader(connection.inputStream.reader()).use { it.readText() }
            android.util.Log.i(tag, "Response body length: ${body.length}")
            val json = JSONObject(body)

            if (!json.optString("status").equals("OK", ignoreCase = true)) {
                android.util.Log.e(tag, "Status not OK: ${json.optString("status")}")
                return@withContext emptyList()
            }

            val dataObj = json.optJSONObject("data")
            if (dataObj == null) {
                android.util.Log.e(tag, "Missing 'data' object in response")
                return@withContext emptyList()
            }
            val companiesArray = dataObj.optJSONArray("companies")
            if (companiesArray == null) {
                android.util.Log.e(tag, "Missing 'companies' array in response")
                return@withContext emptyList()
            }

            val result = mutableListOf<CompanyEntity>()
            for (i in 0 until companiesArray.length()) {
                val c = companiesArray.getJSONObject(i)
                result.add(
                    CompanyEntity(
                        symbol = c.optString("symbol", ""),
                        name = c.optString("name", ""),
                        logo = c.optString("logo", "")
                    )
                )
            }
            android.util.Log.i(tag, "Parsed ${result.size} companies from API")
            return@withContext result
        } catch (e: Exception) {
            android.util.Log.e(tag, "Attempt $attempt — Exception: ${e.javaClass.simpleName}: ${e.message}")
            connection?.disconnect()
            if (attempt < maxRetries) {
                android.util.Log.i(tag, "Retrying in 2 seconds…")
                Thread.sleep(2_000L)
            } else {
                android.util.Log.e(tag, "All $maxRetries attempts failed", e)
            }
        } finally {
            connection?.disconnect()
        }
    }
    emptyList()
}
