package com.assignments.stockmarket.reports_statements.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reusables.ui.theme.PoppinsFamily
import com.assignments.stockmarket.search.SearchItem
import com.assignments.stockmarket.search.SearchItemRow


@Composable
fun ReportsList(filteredItems: List<SearchItem>) {

    if (filteredItems.isEmpty()) {
        Text(
            text = stringResource(R.string.msg_data_not_available),
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = colorResource(R.color.white),
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredItems) { item ->
                ReportCard(title = item.name)
            }
        }
    }
}