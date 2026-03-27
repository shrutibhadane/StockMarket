package com.assignments.stockmarket.bottom_navigation.screens.upi

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.bottom_navigation.screens.upi.components.UpiAppItem
import com.assignments.stockmarket.bottom_navigation.screens.upi.components.openUpiApp
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun UPIScreen(navController: NavController) {

    var amount by remember { mutableStateOf("") }
    var upiId by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary))
            .padding(20.dp)
    ) {

        // Amount Field
        OutlinedTextField(
            value = amount,
            onValueChange = {  input ->
                // ✅ Allow empty (for backspace)
                if (input.isEmpty()) {
                    amount = input
                    return@OutlinedTextField
                }

                // ✅ Limit total length to 20
                if (input.length > 20) return@OutlinedTextField

                // ✅ Allow only digits and dot
                if (input.all { it.isDigit() || it == '.' }) {

                    val parts = input.split(".")

                    // ✅ Only one dot & max 2 decimal places
                    if (parts.size <= 2 && (parts.size == 1 || parts[1].length <= 2)) {

                        // ✅ Prevent starting with dot
                        if (!input.startsWith(".")) {
                            amount = input
                        }
                    }
                }
            },
            label = {
                Text(
                    "Enter Amount",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                    color = colorResource(R.color.white)
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.AccountBalanceWallet,
                    contentDescription = null,
                    tint = colorResource(R.color.white)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),

            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.light_blue_button_bg_color),
                focusedBorderColor = colorResource(R.color.light_blue_button_bg_color),
                unfocusedBorderColor = colorResource(R.color.white),
                focusedLabelColor = colorResource(R.color.light_blue_button_bg_color),
                unfocusedLabelColor = colorResource(R.color.white)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // UPI ID Field
        OutlinedTextField(
            value = upiId,
            onValueChange = { input ->

                // No spaces allowed
                if (input.contains(" ")) return@OutlinedTextField

                // Reject if longer than 50 characters
                if (input.length > 50) return@OutlinedTextField

                // Allow only valid characters
                if (input.all { it.isLetterOrDigit() || it in listOf('.', '_', '-', '@') }) {

                    // Only ONE '@'
                    val atCount = input.count { it == '@' }

                    if (atCount <= 1) {
                        upiId = input.lowercase() // optional: normalize
                    }
                }
            },
            label = {
                Text(
                    "Enter UPI ID (e.g. name@upi)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                    color = colorResource(R.color.white)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.light_blue_button_bg_color),

                focusedBorderColor = colorResource(R.color.light_blue_button_bg_color),
                unfocusedBorderColor = colorResource(R.color.white),

                focusedLabelColor = colorResource(R.color.light_blue_button_bg_color),
                unfocusedLabelColor = colorResource(R.color.white)
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Pay Using",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // UPI Apps Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            // GPay
            UpiAppItem("GPay") {
                openUpiApp(
                    context,
                    upiId,
                    "John Doe",
                    amount,
                    "com.google.android.apps.nbu.paisa.user"
                )
            }

            // PhonePe
            UpiAppItem("PhonePe") {
                openUpiApp(
                    context,
                    upiId,
                    "John Doe",
                    amount,
                    "com.phonepe.app"
                )
            }

            // Paytm
            UpiAppItem("Paytm") {
                openUpiApp(
                    context,
                    upiId,
                    "John Doe",
                    amount,
                    "net.one97.paytm"
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (!isValidUpiId(upiId)) {
                    Toast.makeText(context, "Invalid UPI ID", Toast.LENGTH_SHORT).show()
                    return@Button
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.light_blue_button_bg_color)
            )
        ) {
            Text(
                text = "Proceed to Pay",
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorResource(R.color.bg_primary)
            )
        }
    }
}

fun isValidUpiId(upi: String): Boolean {
    val regex = Regex("^[a-zA-Z0-9._-]{2,}@[a-zA-Z]{2,}$")
    return upi.matches(regex)
}