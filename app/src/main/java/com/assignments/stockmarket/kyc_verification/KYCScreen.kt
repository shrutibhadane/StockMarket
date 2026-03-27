package com.assignments.stockmarket.kyc_verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.kyc_verification.components.AadhaarStep
import com.assignments.stockmarket.kyc_verification.components.KycProgressIndicator
import com.assignments.stockmarket.kyc_verification.components.KycStep
import com.assignments.stockmarket.kyc_verification.components.PanStep
import com.assignments.stockmarket.kyc_verification.components.PersonalDetailsStep
import com.assignments.stockmarket.kyc_verification.components.ReviewStep

@Composable
fun KYCScreen(navController: NavController) {

    var step by remember { mutableStateOf(KycStep.PERSONAL) }

    var fullName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var pan by remember { mutableStateOf("") }
    var aadhaar by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary))
            .padding(20.dp)
    ) {

        KycProgressIndicator(step)

        Spacer(modifier = Modifier.height(40.dp))

        when (step) {

            KycStep.PERSONAL -> {
                PersonalDetailsStep(
                    fullName, dob,
                    onNameChange = { fullName = it },
                    onDobChange = { dob = it },
                    onNext = { step = KycStep.PAN }
                )
            }

            KycStep.PAN -> {
                PanStep(
                    pan,
                    onPanChange = { pan = it },
                    onNext = { step = KycStep.AADHAAR },
                    onBack = { step = KycStep.PERSONAL }
                )
            }

            KycStep.AADHAAR -> {
                AadhaarStep(
                    aadhaar,
                    onChange = { aadhaar = it },
                    onNext = { step = KycStep.REVIEW },
                    onBack = { step = KycStep.PAN }
                )
            }

            KycStep.REVIEW -> {
                ReviewStep(
                    fullName, dob, pan, aadhaar,
                    onSubmit = { /* API call */ },
                    onBack = { step = KycStep.AADHAAR }
                )
            }
        }
    }
}
