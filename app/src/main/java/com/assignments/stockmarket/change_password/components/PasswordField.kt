package com.assignments.stockmarket.change_password.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = AppTextStyles.bold(14),
                )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {

            val icon =
                if (passwordVisible) Icons.Default.Visibility
                else Icons.Default.VisibilityOff

            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Icon(
                    icon,
                    contentDescription = stringResource(R.string.action_toggle_password),
                    tint = colorResource(R.color.white)
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorResource(R.color.white),
            unfocusedTextColor = colorResource(R.color.white),
            focusedBorderColor = colorResource(R.color.bg_button_secondary_light),
            unfocusedBorderColor = colorResource(R.color.white),
            focusedLabelColor = colorResource(R.color.bg_button_secondary_light),
            unfocusedLabelColor = colorResource(R.color.white)
        )
    )
}