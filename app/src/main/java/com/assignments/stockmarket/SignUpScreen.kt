package com.assignments.stockmarket

import android.net.Uri
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
import com.assignments.stockmarket.reusables.CustomTextField
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import kotlinx.coroutines.launch

// Valid TLDs for email validation
private val VALID_TLDS = setOf(
    "com", "in", "org", "net", "edu", "gov", "co", "io", "info", "biz", "us", "uk", "ca", "au"
)

@Composable
fun SignUpScreen(
    navController: NavController,
    onSignUpClick: () -> Unit = {}
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    var apiError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFocused by remember { mutableStateOf(false) }

    // Store generated OTP and email for navigation after dialog OK
    var generatedOtp by remember { mutableStateOf("") }
    var signupEmail by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val signupFailedMessage = stringResource(R.string.signup_failed)
    val otpSentFailedMessage = stringResource(R.string.otp_sent_failed)

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
            .background(color = colorResource(R.color.screen_background))
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔹 Logo
            Image(
                painter = painterResource(id = R.drawable.ic_stock_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.padding(top = 80.dp)
            )

            // 🔹 Sign up Title
            Text(
                text = stringResource(R.string.sign_up),
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 First Name
            CustomTextField(
                placeholder = stringResource(R.string.first_name),
                value = firstName,
                onValueChange = {
                    firstName = it
                    firstNameError = if (it.isNotEmpty() && !it.all { c -> c.isLetter() })
                        "First Name should contain only alphabets"
                    else null
                    apiError = null
                },
                errorMessage = firstNameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Last Name
            CustomTextField(
                placeholder = stringResource(R.string.last_name),
                value = lastName,
                onValueChange = {
                    lastName = it
                    lastNameError = if (it.isNotEmpty() && !it.all { c -> c.isLetter() })
                        "Last Name should contain only alphabets"
                    else null
                    apiError = null
                },
                errorMessage = lastNameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Username
            CustomTextField(
                placeholder = stringResource(R.string.username),
                value = username,
                onValueChange = {
                    username = it
                    val allowedPattern = Regex("^[a-zA-Z0-9#*&]*$")
                    usernameError = when {
                        it.isNotEmpty() && !allowedPattern.matches(it) ->
                            "Username can only contain letters, numbers and #*&"
                        it.isNotEmpty() && it.length <= 5 ->
                            "Username must be more than 5 characters"
                        else -> null
                    }
                    apiError = null
                },
                errorMessage = usernameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Email
            CustomTextField(
                placeholder = stringResource(R.string.email),
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (it.isNotEmpty()) validateEmail(it) else null
                    apiError = null
                },
                errorMessage = emailError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Phone Number
            CustomTextField(
                placeholder = stringResource(R.string.phone_number),
                value = phoneNumber,
                onValueChange = { input ->
                    // Allow only digits
                    val filtered = input.filter { it.isDigit() }
                    phoneNumber = filtered
                    phoneNumberError = if (filtered.isNotEmpty() && (filtered.length < 10 || filtered.length > 15))
                        "Phone number must be 10-15 digits"
                    else null
                    apiError = null
                },
                errorMessage = phoneNumberError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Password
            Box(
                modifier = Modifier.onFocusChanged {
                    isPasswordFocused = it.isFocused || it.hasFocus
                }
            ) {
                CustomTextField(
                    placeholder = stringResource(R.string.password),
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

            // 🔹 Password Criteria List
            if (showPasswordCriteria) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(start = 4.dp)) {
                    PasswordCriteriaRow("At least 8 characters", hasMinLength)
                    PasswordCriteriaRow("At least 1 uppercase letter", hasUpperCase)
                    PasswordCriteriaRow("At least 1 lowercase letter", hasLowerCase)
                    PasswordCriteriaRow("At least 1 digit", hasDigit)
                    PasswordCriteriaRow("At least 1 symbol", hasSymbol)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Confirm Password
            Box(
                modifier = Modifier.onFocusChanged {
                    isConfirmPasswordFocused = it.isFocused || it.hasFocus
                }
            ) {
                CustomTextField(
                    placeholder = stringResource(R.string.confirm_password),
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

            // 🔹 Circular Arrow Button with Loader
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(
                        if (allCriteriaMet)
                            colorResource(R.color.button_background_color)
                        else
                            colorResource(R.color.button_background_color).copy(alpha = 0.4f)
                    )
                    .border(2.dp, colorResource(R.color.white), CircleShape)
                    .clickable {
                        if (isLoading) return@clickable
                        if (!allCriteriaMet) return@clickable

                        // Local validation
                        var valid = true

                        if (firstName.isEmpty()) {
                            firstNameError = "First Name should not be empty"; valid = false
                        } else if (!firstName.all { it.isLetter() }) {
                            firstNameError = "First Name should contain only alphabets"; valid = false
                        }

                        if (lastName.isEmpty()) {
                            lastNameError = "Last Name should not be empty"; valid = false
                        } else if (!lastName.all { it.isLetter() }) {
                            lastNameError = "Last Name should contain only alphabets"; valid = false
                        }

                        val usernameAllowed = Regex("^[a-zA-Z0-9#*&]*$")
                        if (username.isEmpty()) {
                            usernameError = "Username should not be empty"; valid = false
                        } else if (!usernameAllowed.matches(username)) {
                            usernameError = "Username can only contain letters, numbers and #*&"; valid = false
                        } else if (username.length <= 5) {
                            usernameError = "Username must be more than 5 characters"; valid = false
                        }

                        if (email.isEmpty()) {
                            emailError = "Email should not be empty"; valid = false
                        } else {
                            val emailValidation = validateEmail(email)
                            if (emailValidation != null) {
                                emailError = emailValidation; valid = false
                            }
                        }

                        if (phoneNumber.isEmpty()) {
                            phoneNumberError = "Phone Number should not be empty"; valid = false
                        } else if (phoneNumber.length < 10 || phoneNumber.length > 15) {
                            phoneNumberError = "Phone number must be 10-15 digits"; valid = false
                        }

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
                        coroutineScope.launch {
                            val result = registerUser(
                                firstName = firstName.trim(),
                                lastName = lastName.trim(),
                                username = username.trim(),
                                email = email.trim(),
                                phoneNumber = phoneNumber.trim(),
                                password = password.trim()
                            )
                            if (result.success) {
                                // Generate OTP and send via API
                                val otp = (1000..9999).random().toString()
                                val otpSent = sendOtpApi(email.trim(), otp)
                                isLoading = false
                                if (otpSent) {
                                    generatedOtp = otp
                                    signupEmail = email.trim()
                                    onSignUpClick()
                                    showSuccessDialog = true
                                } else {
                                    apiError = otpSentFailedMessage
                                }
                            } else {
                                isLoading = false
                                apiError = result.message ?: signupFailedMessage
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
                        contentDescription = stringResource(R.string.sign_up),
                        tint = if (allCriteriaMet) colorResource(R.color.white) else colorResource(R.color.white).copy(alpha = 0.4f),
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

            Spacer(modifier = Modifier.weight(2f))

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Bottom Text
            Text(
                text = stringResource(R.string.already_have_an_account_click_to_login),
                color = colorResource(R.color.white),
                fontSize = 12.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable { navController.navigate("login") }
            )
        }
    }

    // 🔹 Success Dialog — navigate to OTP screen on Ok
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { /* Prevent dismiss by tapping outside */ },
            title = {
                Text(
                    text = "Congratulations! 🎉",
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    text = "Your account has been created successfully!",
                    fontFamily = PoppinsFamily,
                    fontSize = 15.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        val encodedEmail = Uri.encode(signupEmail)
                        navController.navigate("otp/$encodedEmail/$generatedOtp") {
                            popUpTo("sign_up") { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        text = "Ok",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        )
    }
}

@Composable
private fun PasswordCriteriaRow(label: String, met: Boolean) {
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
            color = if (met) Color.Green else Color.Red,
            fontSize = 12.sp,
            fontFamily = PoppinsFamily
        )
    }
}

private fun validateEmail(email: String): String? {
    if ("@" !in email) return "Email must contain @"
    val parts = email.split("@")
    if (parts.size != 2 || parts[0].isEmpty()) return "Invalid email format"
    val domain = parts[1]
    if ("." !in domain) return "Email must have a valid domain"
    val tld = domain.substringAfterLast(".").lowercase()
    if (tld.isEmpty()) return "Email must have a valid TLD"
    if (tld !in VALID_TLDS) return "Invalid TLD. Use .com, .in, .org, etc."
    return null
}

