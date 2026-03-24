package com.assignments.stockmarket.kyc_verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R

@Composable
fun KycProgressIndicator(step: KycStep) {

    val steps = listOf(
        stringResource(R.string.personal),
        stringResource(R.string.pan),
        stringResource(R.string.aadhaar),
        stringResource(R.string.review)
    )
    val currentIndex = step.ordinal

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        steps.forEachIndexed { index, title ->

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            if (index <= currentIndex)
                                colorResource(R.color.light_blue_button_bg_color)
                            else
                                colorResource(R.color.light_grey_text_color),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${index + 1}", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(title, fontSize = 10.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(20.dp))

        }
    }
}