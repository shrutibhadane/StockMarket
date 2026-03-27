package com.assignments.stockmarket.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily

@Composable
fun SearchScreen(
    navController: NavController
) {
    val query = remember { mutableStateOf(TextFieldValue()) } // Track the query text

    // Define a list of search items
    val allItems = listOf(
        SearchItem("Franklin India Opportunities Direct Fund"),
        SearchItem("LG Electronics India Ltd."),
        SearchItem("Apollo Tyres Ltd."),
        SearchItem("Multi Commodity Exchange of India Ltd.")
    )


    // Filter the search items based on the query
    val filteredItems = allItems.filter { it.name.contains(query.value.text, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        SearchTextField(query = query, navController, R.drawable.ic_back_arrow)

        HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)

        if (filteredItems.isEmpty()) {
            Text(
                text = stringResource(R.string.msg_data_not_available),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            // LazyColumn for displaying filtered items
            LazyColumn(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(filteredItems) { item ->
                    SearchItemRow(item)
                }
            }
        }
    }
}