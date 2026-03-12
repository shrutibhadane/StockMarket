package com.assignments.stockmarket.tabs.orders

data class Orders(
    val time: String,
    val status: String,
    val name: String,
    val quantity: String,
    val type: String,
    val avgValue: String
)