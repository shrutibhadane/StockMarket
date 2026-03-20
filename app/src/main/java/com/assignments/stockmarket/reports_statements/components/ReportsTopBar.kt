package com.assignments.stockmarket.reports_statements.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsTopBar() {
    TopAppBar(
        title = {
            Text("Reports & Statements")
        },
        actions = {
            IconButton(onClick = { /* filter */ }) {
                Icon(Icons.Default.FilterList, contentDescription = null)
            }
        }
    )
}