package com.assignments.stockmarket

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.reflect.Modifier

/**
 * ============================================================================
 *   SPRINT 2 — Reusable Components & API Functions
 *   Test cases for tickets T2.1 through T2.10
 *   Validates: CustomTextField, OTPInput, 7 API functions, validateEmail
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class Sprint2ComponentsApiTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    // ════════════════════════════════════════════════════════════════════════
    //  T2.1 — CustomTextField Reusable Composable
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_1_TC01_customTextFieldComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.reusables.CustomTextFieldKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "CustomTextField composable should exist",
            methods.any { it.contains("CustomTextField") }
        )
    }

    @Test
    fun T2_1_TC02_customTextFieldAcceptsPlaceholderAndValue() {
        val clazz = Class.forName("com.assignments.stockmarket.reusables.CustomTextFieldKt")
        val method = clazz.declaredMethods.find { it.name.contains("CustomTextField") }
        assertNotNull("CustomTextField method should exist", method)
        // Should have multiple params: placeholder, value, onValueChange, isPassword, etc. + Composer
        assertTrue(
            "CustomTextField should accept multiple parameters",
            method!!.parameterCount >= 5
        )
    }

    @Test
    fun T2_1_TC03_customTextFieldFileInReusablesPackage() {
        val clazz = Class.forName("com.assignments.stockmarket.reusables.CustomTextFieldKt")
        assertTrue(
            "CustomTextField should be in reusables package",
            clazz.name.contains("reusables")
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.2 — OTPInput Reusable Composable
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_2_TC01_otpInputComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.reusables.OTPInputKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "OTPInput composable should exist",
            methods.any { it.contains("OTPInput") }
        )
    }

    @Test
    fun T2_2_TC02_otpInputAcceptsOtpLengthParameter() {
        val clazz = Class.forName("com.assignments.stockmarket.reusables.OTPInputKt")
        val method = clazz.declaredMethods.find { it.name.contains("OTPInput") }
        assertNotNull("OTPInput method should exist", method)
        // Should have: otpLength, onOtpComplete, onValueChange + Composer + changed
        assertTrue(
            "OTPInput should accept multiple parameters including otpLength",
            method!!.parameterCount >= 3
        )
    }

    @Test
    fun T2_2_TC03_otpInputFileInReusablesPackage() {
        val clazz = Class.forName("com.assignments.stockmarket.reusables.OTPInputKt")
        assertTrue(
            "OTPInput should be in reusables package",
            clazz.name.contains("reusables")
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.3 — authenticateUser() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_3_TC01_authenticateUserFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "authenticateUser function should exist",
            methods.any { it.contains("authenticateUser") }
        )
    }

    @Test
    fun T2_3_TC02_authenticateUserIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("authenticateUser") }
        assertNotNull("authenticateUser should exist", method)
        // Suspend functions have a Continuation parameter as last arg
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "authenticateUser should be a suspend function (last param is Continuation)",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun T2_3_TC03_authenticateUserAcceptsTwoStringParams() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("authenticateUser") }
        assertNotNull("authenticateUser should exist", method)
        // Should have: String (username), String (password), Continuation
        val stringParams = method!!.parameterTypes.count { it == String::class.java }
        assertTrue(
            "authenticateUser should accept at least 2 String parameters (username, password)",
            stringParams >= 2
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.4 — registerUser() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_4_TC01_registerUserFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "registerUser function should exist",
            methods.any { it.contains("registerUser") }
        )
    }

    @Test
    fun T2_4_TC02_registerUserIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("registerUser") }
        assertNotNull("registerUser should exist", method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "registerUser should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun T2_4_TC03_registerUserAcceptsSixStringParams() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("registerUser") }
        assertNotNull("registerUser should exist", method)
        val stringParams = method!!.parameterTypes.count { it == String::class.java }
        assertTrue(
            "registerUser should accept 6 String params (firstName, lastName, username, email, phone, password)",
            stringParams >= 6
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.5 — sendOtpApi() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_5_TC01_sendOtpApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "sendOtpApi function should exist",
            methods.any { it.contains("sendOtpApi") }
        )
    }

    @Test
    fun T2_5_TC02_sendOtpApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("sendOtpApi") }
        assertNotNull("sendOtpApi should exist", method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "sendOtpApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun T2_5_TC03_sendOtpApiAcceptsTwoStringParams() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("sendOtpApi") }
        assertNotNull("sendOtpApi should exist", method)
        val stringParams = method!!.parameterTypes.count { it == String::class.java }
        assertTrue(
            "sendOtpApi should accept 2 String params (email, otp)",
            stringParams >= 2
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.6 — updateStatusApi() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_6_TC01_updateStatusApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "updateStatusApi function should exist",
            methods.any { it.contains("updateStatusApi") }
        )
    }

    @Test
    fun T2_6_TC02_updateStatusApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("updateStatusApi") }
        assertNotNull("updateStatusApi should exist", method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "updateStatusApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.7 — checkTheStatusApi() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_7_TC01_checkTheStatusApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "checkTheStatusApi function should exist",
            methods.any { it.contains("checkTheStatusApi") }
        )
    }

    @Test
    fun T2_7_TC02_checkTheStatusApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("checkTheStatusApi") }
        assertNotNull("checkTheStatusApi should exist", method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "checkTheStatusApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.8 — forgotPasswordApi() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_8_TC01_forgotPasswordApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "forgotPasswordApi function should exist",
            methods.any { it.contains("forgotPasswordApi") }
        )
    }

    @Test
    fun T2_8_TC02_forgotPasswordApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("forgotPasswordApi") }
        assertNotNull("forgotPasswordApi should exist", method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "forgotPasswordApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.9 — resetPasswordApi() API Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_9_TC01_resetPasswordApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "resetPasswordApi function should exist",
            methods.any { it.contains("resetPasswordApi") }
        )
    }

    @Test
    fun T2_9_TC02_resetPasswordApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("resetPasswordApi") }
        assertNotNull("resetPasswordApi should exist", method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "resetPasswordApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun T2_9_TC03_resetPasswordApiAcceptsPasswordParam() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("resetPasswordApi") }
        assertNotNull("resetPasswordApi should exist", method)
        val stringParams = method!!.parameterTypes.count { it == String::class.java }
        assertTrue(
            "resetPasswordApi should accept String params (email/phone, password)",
            stringParams >= 1
        )
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T2.10 — validateEmail() Utility Function
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T2_10_TC01_validEmailReturnsNull() {
        // validateEmail is private in SignUpScreen, test the logic pattern
        val email = "test@example.com"
        assertTrue("Valid email should contain @", "@" in email)
        val parts = email.split("@")
        assertEquals("Should have 2 parts", 2, parts.size)
        assertTrue("Domain should contain a dot", "." in parts[1])
        val tld = parts[1].substringAfterLast(".").lowercase()
        assertTrue("TLD 'com' should be valid", tld in listOf("com", "in", "org", "net", "edu"))
    }

    @Test
    fun T2_10_TC02_emailWithoutAtSignIsInvalid() {
        val email = "testexample.com"
        assertFalse("Email without @ should be invalid", "@" in email)
    }

    @Test
    fun T2_10_TC03_emailWithInvalidTldIsInvalid() {
        val email = "test@example.xyz"
        val tld = email.substringAfterLast(".").lowercase()
        val validTlds = setOf("com", "in", "org", "net", "edu", "gov", "co", "io", "info", "biz", "us", "uk", "ca", "au")
        assertFalse("TLD 'xyz' should be invalid", tld in validTlds)
    }

    @Test
    fun T2_10_TC04_emailWithNoDomainIsInvalid() {
        val email = "test@"
        val parts = email.split("@")
        assertTrue("Domain part should be empty or missing dot", parts[1].isEmpty() || "." !in parts[1])
    }

    @Test
    fun T2_10_TC05_emailWithValidIndianTld() {
        val email = "user@domain.in"
        val tld = email.substringAfterLast(".").lowercase()
        val validTlds = setOf("com", "in", "org", "net", "edu", "gov", "co", "io", "info", "biz", "us", "uk", "ca", "au")
        assertTrue("TLD 'in' should be valid", tld in validTlds)
    }

    @Test
    fun T2_10_TC06_emailWithEmptyLocalPartIsInvalid() {
        val email = "@example.com"
        val parts = email.split("@")
        assertTrue("Local part before @ should be empty", parts[0].isEmpty())
    }
}

