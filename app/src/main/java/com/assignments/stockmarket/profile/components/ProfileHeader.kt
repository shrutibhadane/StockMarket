package com.assignments.stockmarket.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun ProfileHeader() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            tint = colorResource(R.color.white),
            contentDescription = "Profile",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(1.dp, colorResource(R.color.white), CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Shruti Bhadane",
            color = colorResource(R.color.white),
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}