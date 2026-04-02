package com.assignments.stockmarket.kyc_verification.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import java.util.Calendar

@Composable
fun PersonalDetailsStep(
    name: String,
    dob: String,
    onNameChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    onNext: () -> Unit
) {

    val isValid = name.isNotBlank() && dob.isNotBlank()

    Column {

        Text(stringResource(R.string.label_personal_details),
            style = AppTextStyles.regular(18),
            )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { input ->
                onNameChange(input)
            },
            label = {
                Text(
                    stringResource(R.string.label_full_name),
                    style = AppTextStyles.bold(12),
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

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),

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

        Spacer(Modifier.height(12.dp))

        val context = LocalContext.current

        val calendar = remember { Calendar.getInstance() }

        val datePickerDialog = remember {
            DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate =
                        String.format(
                            "%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )
                    onDobChange(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                // Restrict future dates (important for KYC)
                datePicker.maxDate = System.currentTimeMillis()
            }
        }

        val interactionSource = remember { MutableInteractionSource() }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect {
                datePickerDialog.show()
            }
        }

        OutlinedTextField(
            value = dob,
            onValueChange = {},
            label = {
                Text(
                    stringResource(R.string.label_dob),
                    style = AppTextStyles.bold(12),
                    )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = colorResource(R.color.white)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerDialog.show() // open calendar
                },
            readOnly = true,
            singleLine = true,
            interactionSource = interactionSource,
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

        Button(
            onClick = onNext,
            enabled = isValid,
            modifier = Modifier
                .fillMaxWidth() // full width
                .height(40.dp), // nice standard height
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.bg_button_secondary_light),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.text_secondary),
                disabledContentColor = colorResource(R.color.white)
            )
        ) {
            Text(
                text = stringResource(R.string.action_continue),
                style = AppTextStyles.bold(14,
                colorResource(R.color.bg_primary)
                )
            )
        }

    }
}