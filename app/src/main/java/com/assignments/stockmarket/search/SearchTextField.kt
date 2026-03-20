package com.assignments.stockmarket.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R

@Composable
fun SearchTextField(
    query: MutableState<TextFieldValue>, navController: NavController,
    backIconRes: Int? = null, ) {
    BasicTextField(
        value = query.value,
        onValueChange = { newText -> query.value = newText },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            color = colorResource(R.color.white),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        minLines = 1,
        maxLines = 1,
        cursorBrush = SolidColor(colorResource(R.color.white)),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Start, // Left alignment for the items
                verticalAlignment = Alignment.CenterVertically
            ) {
                backIconRes?.let { icon ->
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = colorResource(R.color.light_grey_text_color),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Text field will take the remaining space between the arrow and search icon
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Search icon at the trailing end
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    )
}