package com.assignments.stockmarket.investments.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily

@Composable
fun NumberKeypad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {

    val buttonModifier = Modifier
        .size(70.dp)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        KeypadRow(listOf("1", "2", "3"), onNumberClick)
        KeypadRow(listOf("4", "5", "6"), onNumberClick)
        KeypadRow(listOf("7", "8", "9"), onNumberClick)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            KeypadButton(".", buttonModifier) { onNumberClick(".") }

            KeypadButton("0", buttonModifier) { onNumberClick("0") }

            IconButton(
                onClick = onDeleteClick,
                modifier = buttonModifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_space),
                    contentDescription = "Backspace",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun KeypadRow(
    numbers: List<String>,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        numbers.forEach { number ->
            KeypadButton(number, Modifier.size(70.dp)) {
                onClick(number)
            }
        }
    }
}

@Composable
fun KeypadButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorResource(R.color.white),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFamily
        )
    }
}