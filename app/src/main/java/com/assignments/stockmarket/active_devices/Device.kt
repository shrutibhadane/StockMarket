package com.assignments.stockmarket.active_devices

data class Device(
    val name: String,
    val location: String,
    val lastActive: String,
    val isCurrent: Boolean
)

val devices = listOf(
    Device("Samsung Galaxy S21", "Mumbai, India", "Active now", true),
    Device("iPhone 13", "Delhi, India", "2 hours ago", false),
    Device("OnePlus 9", "Pune, India", "Yesterday", false)
)