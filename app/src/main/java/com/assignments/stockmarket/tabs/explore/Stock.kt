package com.assignments.stockmarket.tabs.explore

data class StockDetails(
    val id: Int,
    val name: String,
    val risk: String,
    val category: String,
    val theme: String,
    val annualReturn: Double,
    val annualReturnPeriod: String,
    val dayChange: Double,
    val investedAmount: Double,
    val totalReturns: Double,
    val isPositive: Boolean
)

val stockDetailsList = listOf(

    StockDetails(
        id = 1,
        name = "Tejas Networks",
        risk = "Very High Risk",
        category = "Equity",
        theme = "Thematic",
        annualReturn = 28.24,
        annualReturnPeriod = "3Y annualised",
        dayChange = -2.07,
        investedAmount = 3000.0,
        totalReturns = -3.12,
        isPositive = true
    ),

    StockDetails(
        id = 2,
        name = "Reliance Industries",
        risk = "High Risk",
        category = "Equity",
        theme = "Energy",
        annualReturn = 18.50,
        annualReturnPeriod = "3Y annualised",
        dayChange = 1.75,
        investedAmount = 5000.0,
        totalReturns = 6.40,
        isPositive = false
    ),

    StockDetails(
        id = 3,
        name = "Paras Defence",
        risk = "Very High Risk",
        category = "Equity",
        theme = "Defence",
        annualReturn = 34.10,
        annualReturnPeriod = "3Y annualised",
        dayChange = 3.20,
        investedAmount = 2000.0,
        totalReturns = 12.80,
        isPositive = true
    ),

    StockDetails(
        id = 4,
        name = "Reliance",
        risk = "High Risk",
        category = "Equity",
        theme = "Energy",
        annualReturn = 18.50,
        annualReturnPeriod = "3Y annualised",
        dayChange = 1.75,
        investedAmount = 5000.0,
        totalReturns = 6.40,
        isPositive = false
    ),

    StockDetails(
        id = 5,
        name = "Jio",
        risk = "Very High Risk",
        category = "Equity",
        theme = "Defence",
        annualReturn = 34.10,
        annualReturnPeriod = "3Y annualised",
        dayChange = 3.20,
        investedAmount = 2000.0,
        totalReturns = 12.80,
        isPositive = true
    )

)