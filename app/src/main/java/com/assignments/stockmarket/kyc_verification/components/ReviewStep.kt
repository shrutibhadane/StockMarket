package com.assignments.stockmarket.kyc_verification.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun ReviewStep(
    name: String,
    dob: String,
    pan: String,
    aadhaar: String,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {

    Column {

        Text(
            stringResource(R.string.label_review_details),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Name: $name",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "DOB: $dob", fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "PAN: $pan", fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Aadhaar: XXXX XXXX ${aadhaar.takeLast(4)}", fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            color = colorResource(R.color.white)
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
                    color = colorResource(R.color.bg_primary)
                )
            }

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = onSubmit,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.bg_button_secondary_light),
                    contentColor = colorResource(R.color.bg_primary),
                    disabledContainerColor = colorResource(R.color.text_secondary),
                    disabledContentColor = colorResource(R.color.bg_primary)
                )
            ) {
                Text(
                    stringResource(R.string.action_submit_kyc),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                    color = colorResource(R.color.bg_primary)
                )
            }
        }
    }
}