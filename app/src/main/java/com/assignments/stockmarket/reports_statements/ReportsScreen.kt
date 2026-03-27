package com.assignments.stockmarket.reports_statements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignments.stockmarket.R
import com.assignments.stockmarket.reports_statements.components.ReportsList
import com.assignments.stockmarket.reports_statements.components.SearchAndFilterSection
import com.assignments.stockmarket.reusables.app_bar.AppBarBackArrow
import com.assignments.stockmarket.search.SearchItem
import com.assignments.stockmarket.search.SearchTextField

@Composable
fun ReportsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val query = remember { mutableStateOf(TextFieldValue()) }

    val allItems = listOf(
        SearchItem("Monthly Statement - Feb 2026"),
        SearchItem("Expense Report - Q1"),
        SearchItem("Tax Summary 2025"),
        SearchItem("Tax Summary 2026")
    )

    // Filter the search items based on the query
    val filteredItems = allItems.filter { it.name.contains(query.value.text, ignoreCase = true) }

    Scaffold(
        topBar = {
            AppBarBackArrow(
                navController = navController,
                title = "Reports & Statements"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            SearchTextField(query = query, navController)

            HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)

            SearchAndFilterSection()

            Spacer(modifier = Modifier.height(20.dp))

            ReportTabs(navController)

            Spacer(modifier = Modifier.height(12.dp))

            ReportsList(filteredItems)
        }
    }
}

@Composable
fun ReportTabs(navController: NavController) {

    val tabItems = listOf("Reports", "Statements", "Tax")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = colorResource(id = R.color.bg_primary),
            contentColor = colorResource(R.color.white),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = colorResource(R.color.white),
                )
            }
        ) {

            tabItems.forEachIndexed { index, title ->

                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    selectedContentColor = colorResource(R.color.white),
                    unselectedContentColor = colorResource(R.color.white).copy(alpha = 0.6f),
                    text = {
                        Text(
                            title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                )
            }
        }

        /*when (selectedTabIndex) {
            0 -> ExploreScreen(navController)
            1 -> HoldingsScreen(navController)
            2 -> PositionsScreen(navController)
        }*/
    }
}