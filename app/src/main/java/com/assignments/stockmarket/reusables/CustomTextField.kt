package com.assignments.stockmarket.reusables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R

@Composable
fun CustomTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isConfirmPassword: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            placeholder = {
                Text(
                    text = placeholder,
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                )
            },
            isError = errorMessage != null,
            singleLine = true,
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    val image = if (passwordVisible)
                        androidx.compose.material.icons.Icons.Filled.Visibility
                    else androidx.compose.material.icons.Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description, tint = colorResource(R.color.white))
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(3.dp, if (errorMessage != null) Color.White else Color.White),
                    RoundedCornerShape(10.dp)
                ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.bg_text_field),
                focusedContainerColor = colorResource(R.color.bg_text_field),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = colorResource(R.color.white),
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.white),
                errorBorderColor = Color.Transparent,
                errorContainerColor = colorResource(R.color.bg_text_field),
                errorCursorColor = colorResource(R.color.white),
                errorTextColor = colorResource(R.color.white),
            )
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = colorResource(R.color.white),
                fontSize = 12.sp,
                modifier = Modifier
            )
        }
    }
}