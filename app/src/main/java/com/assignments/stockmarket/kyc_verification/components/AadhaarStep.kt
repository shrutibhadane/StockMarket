package com.assignments.stockmarket.kyc_verification.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.kyc_verification.isValidAadhaar
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun AadhaarStep(
    aadhaar: String,
    onChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {

    val isValid = isValidAadhaar(aadhaar)

    Column {

        Text(
            stringResource(R.string.action_verify_aadhaar),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white))

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = aadhaar,
            onValueChange = {
                if (it.all { ch -> ch.isDigit() } && it.length <= 12) {
                    onChange(it)
                }
            },
            label = {
                Text(
                    stringResource(R.string.label_aadhaar_number),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                    color = colorResource(R.color.white)
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = colorResource(R.color.white)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.white),
                cursorColor = colorResource(R.color.bg_button_secondary_light),
                focusedBorderColor = colorResource(R.color.bg_button_secondary_light),
                unfocusedBorderColor = colorResource(R.color.white),
                focusedLabelColor = colorResource(R.color.bg_button_secondary_light),
                unfocusedLabelColor = colorResource(R.color.white)
            )
        )

        Spacer(Modifier.height(30.dp))

        Row {
            Button(
                onClick = onBack, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.bg_button_secondary_light),
                    contentColor = colorResource(R.color.bg_primary),
                    disabledContainerColor = colorResource(R.color.text_secondary),
                    disabledContentColor = colorResource(R.color.bg_primary)
                )
            ) {
                Text(
                    stringResource(R.string.action_back),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                )
            }

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = onNext,
                enabled = isValid,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.bg_button_secondary_light),
                    contentColor = colorResource(R.color.bg_primary),
                    disabledContainerColor = colorResource(R.color.text_secondary),
                    disabledContentColor = colorResource(R.color.bg_primary)
                )
            ) {
                Text(
                    stringResource(R.string.label_verify_aadhaar),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                    color = colorResource(R.color.bg_primary)
                )
            }
        }
    }
}