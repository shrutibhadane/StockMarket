package com.assignments.stockmarket.reusables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPInput(
    otpLength: Int = 4,
    onOtpComplete: (String) -> Unit = {}
) {
    val focusRequesters = List(otpLength) { FocusRequester() }
    val otpValues = remember { mutableStateListOf(*Array(otpLength) { "" }) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 0 until otpLength) {

            OutlinedTextField(
                value = otpValues[i],
                onValueChange = { value ->
                    if (value.length <= 1 && value.all { it.isDigit() }) {
                        otpValues[i] = value

                        if (value.isNotEmpty()) {
                            if (i < otpLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            } else {
                                onOtpComplete(otpValues.joinToString(""))
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(70.dp)
                    .focusRequester(focusRequesters[i]),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color.White
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                    focusedContainerColor = Color(0xFF2C3A59),
                    unfocusedContainerColor = Color(0xFF2C3A59),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}