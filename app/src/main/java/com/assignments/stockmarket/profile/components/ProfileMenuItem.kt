package com.assignments.stockmarket.profile.components

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    actionText: String? = null
) {

    val context = LocalContext.current

    val actionButtonWidth = 72.dp
    val actionButtonHeight = 28.dp

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorResource(R.color.white),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = colorResource(R.color.white),
                modifier = Modifier.weight(1f)
            )

            if (actionText != null) {
                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                context.getString(R.string.msg_invite)
                            )
                        }

                        context.startActivity(
                            Intent.createChooser(shareIntent, "Invite via")
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.bg_button_secondary_light)
                    ),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .width(actionButtonWidth)
                        .height(actionButtonHeight)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Whatsapp,
                            contentDescription = "Invite",
                            modifier = Modifier.size(12.dp),
                            tint = colorResource(R.color.bg_primary),
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = actionText,
                            color = colorResource(R.color.bg_primary),
                            fontFamily = PoppinsFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            } else {
                Spacer(
                    modifier = Modifier
                        .width(actionButtonWidth)
                        .height(actionButtonHeight)
                )
            }
        }

        HorizontalDivider(color = colorResource(R.color.white).copy(alpha = 0.3f))
    }
}
