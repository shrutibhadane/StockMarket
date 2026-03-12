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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import com.assignments.stockmarket.reusables.OTPInput
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MPINScreen (
    navController: NavController,
    onMPINClick: () -> Unit = {}
) {
    var enteredMpin by remember { mutableStateOf("") }
    var mpinError by remember { mutableStateOf<String?>(null) }
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

            // 🔹 MPIN
            Text(
                text = stringResource(R.string.mpin),
                color = colorResource(R.color.white),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))

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
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                Paper.book().write("user_mpin", enteredMpin)
                            }
                            navController.navigate("welcome") {
                                popUpTo("mpin") { inclusive = true }
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