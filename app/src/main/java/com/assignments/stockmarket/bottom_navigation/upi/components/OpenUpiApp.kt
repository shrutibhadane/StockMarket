package com.assignments.stockmarket.bottom_navigation.upi.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openUpiApp(context: Context, upiId: String, name: String, amount: String, packageName: String) {
    if (upiId.isEmpty() || amount.isEmpty()) {
        Toast.makeText(context, "Enter valid amount and UPI ID", Toast.LENGTH_SHORT).show()
        return
    }

    val uri = Uri.parse(
        "upi://pay?pa=$upiId&pn=$name&tn=Stock+Market+Deposit&am=$amount&cu=INR"
    )

    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage(packageName) // Open a specific app

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "App not installed", Toast.LENGTH_SHORT).show()
    }
}