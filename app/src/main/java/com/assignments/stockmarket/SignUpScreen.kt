package com.assignments.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.assignments.stockmarket.reusables.CustomTextField
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.ui.theme.StockMarketTheme

@Composable
fun SignUpScreen(
    navController: NavController,
    onSignUpClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

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

            // 🔹 Sign up
            Text(
                text = stringResource(R.string.sign_up),
                color = Color.White,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))

            // 🔹 Fullname
            CustomTextField(
                placeholder = stringResource(R.string.fullname),
                value = fullName,
                onValueChange = {
                    fullName = it
                    if (it.isNotEmpty()) fullNameError = null
                },
                errorMessage = fullNameError
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Username Field
            CustomTextField(
                placeholder = stringResource(R.string.username),
                value = username,
                onValueChange = {
                    username = it
                    if (it.isNotEmpty()) usernameError = null
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

                    passwordError =
                        if (it.isEmpty()) "Password should not be empty"
                        else null

                    if (confirmPassword.isNotEmpty() && confirmPassword != it) {
                        confirmPasswordError = "Passwords do not match"
                    } else {
                        confirmPasswordError = null
                    }
                },
                isPassword = true,
                errorMessage = passwordError
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Confirm Password Field
            CustomTextField(
                placeholder = stringResource(R.string.confirm_password),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it

                    confirmPasswordError =
                        when {
                            it.isEmpty() -> "Confirm Password should not be empty"
                            password != it -> "Passwords do not match"
                            else -> null
                        }
                },
                isPassword = true,
                errorMessage = confirmPasswordError
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Circular Arrow Button
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.button_background_color))
                    .border(2.dp, Color.White, CircleShape)
                    .clickable {
                        // Validate before proceeding
                        var valid = true
                        if (fullName.isEmpty()) {
                            fullNameError = "Fullname should not be empty"
                            valid = false
                        }
                        if (username.isEmpty()) {
                            usernameError = "Username should not be empty"
                            valid = false
                        }
                        if (password.isEmpty()) {
                            passwordError = "Password should not be empty"
                            valid = false
                        }
                        if (confirmPassword.isEmpty()) {
                            confirmPasswordError = "Confirm Password should not be empty"
                            valid = false
                        }
                        // 🔹 Password match validation
                        if (password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                            if (password != confirmPassword) {
                                confirmPasswordError = "Passwords do not match"
                                valid = false
                            }
                        }
                        if (valid) {
                            navController.navigate("mpin")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.sign_up),
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔹 Bottom Text
            Text(
                text = stringResource(R.string.already_have_an_account_click_to_login),
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable {
                        navController.navigate("login")
                    }
            )
        }
    }
}