package com.assignments.stockmarket.reports_statements.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles

@Composable
fun ReportCard(title: String) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.bg_text_field)
        ),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.25f)
        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                style = AppTextStyles.bold(12
                ),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Feb 28, 2026 • 1.2 MB",
                style = AppTextStyles.bold(12
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { /* view */ },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                    modifier = Modifier.height(28.dp) // reduce height
                ) {
                    Text(
                        "View",
                        style = AppTextStyles.bold(12),
                    )
                }

                TextButton(
                    onClick = { /* download */ },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        "Download",
                        style = AppTextStyles.bold(12),
                    )
                }
            }
        }
    }
}