package com.assignments.stockmarket.kyc_verification

fun isValidPan(pan: String): Boolean {
    val regex = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")
    return pan.matches(regex)
}

fun isValidAadhaar(aadhaar: String): Boolean {
    return aadhaar.length == 12
}

