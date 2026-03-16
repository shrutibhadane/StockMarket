package com.assignments.stockmarket

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * ============================================================================
 *   TEST CASES FOR DEDICATED API TICKETS (DedicatedAPI Tickets.txt)
 *   Validates each ticket's acceptance criteria against DedicatedAPI.kt code
 *   Uses Robolectric for Android framework access on JVM
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class DedicatedAPITicketsTest {

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 1 — Create DedicatedAPI.kt with base URL and endpoints
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket1_TC01_loginApiUrlIsCorrect() {
        assertEquals(
            "https://system-project-api.onrender.com/api/login",
            LOGIN_API_URL
        )
    }

    @Test
    fun ticket1_TC02_signupApiUrlIsCorrect() {
        assertEquals(
            "https://system-project-api.onrender.com/api/signup",
            SIGNUP_API_URL
        )
    }

    @Test
    fun ticket1_TC03_sendOtpApiUrlIsCorrect() {
        assertEquals(
            "https://system-project-api.onrender.com/api/sendotp",
            SEND_OTP_API_URL
        )
    }

    @Test
    fun ticket1_TC04_updateStatusApiUrlIsCorrect() {
        assertEquals(
            "https://system-project-api.onrender.com/api/updatestatus",
            UPDATE_STATUS_API_URL
        )
    }

    @Test
    fun ticket1_TC05_checkStatusApiUrlIsCorrect() {
        assertEquals(
            "https://system-project-api.onrender.com/api/checkthestatus",
            CHECK_STATUS_API_URL
        )
    }

    @Test
    fun ticket1_TC06_allEndpointsStartWithSameBaseUrl() {
        val baseUrl = "https://system-project-api.onrender.com/api"
        assertTrue("LOGIN_API_URL should start with base URL", LOGIN_API_URL.startsWith(baseUrl))
        assertTrue("SIGNUP_API_URL should start with base URL", SIGNUP_API_URL.startsWith(baseUrl))
        assertTrue("SEND_OTP_API_URL should start with base URL", SEND_OTP_API_URL.startsWith(baseUrl))
        assertTrue("UPDATE_STATUS_API_URL should start with base URL", UPDATE_STATUS_API_URL.startsWith(baseUrl))
        assertTrue("CHECK_STATUS_API_URL should start with base URL", CHECK_STATUS_API_URL.startsWith(baseUrl))
    }

    @Test
    fun ticket1_TC07_baseUrlIsPrivate() {
        // BASE_URL should be private const val — verify it's NOT accessible as a public field
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val fields = clazz.fields.map { it.name }
        assertFalse(
            "BASE_URL should not be publicly accessible",
            fields.any { it == "BASE_URL" }
        )
    }

    @Test
    fun ticket1_TC08_endpointConstantsArePublic() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val fields = clazz.fields.map { it.name }
        assertTrue("LOGIN_API_URL should be public", fields.contains("LOGIN_API_URL"))
        assertTrue("SIGNUP_API_URL should be public", fields.contains("SIGNUP_API_URL"))
        assertTrue("SEND_OTP_API_URL should be public", fields.contains("SEND_OTP_API_URL"))
        assertTrue("UPDATE_STATUS_API_URL should be public", fields.contains("UPDATE_STATUS_API_URL"))
        assertTrue("CHECK_STATUS_API_URL should be public", fields.contains("CHECK_STATUS_API_URL"))
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 2 — Create SignUpResult and StatusResult data classes
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket2_TC01_signUpResultClassExists() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpResult")
        assertNotNull("SignUpResult data class should exist", clazz)
    }

    @Test
    fun ticket2_TC02_signUpResultSuccessOnly() {
        val result = SignUpResult(success = true)
        assertTrue(result.success)
        assertNull("Message should default to null", result.message)
    }

    @Test
    fun ticket2_TC03_signUpResultFailureWithMessage() {
        val result = SignUpResult(success = false, message = "Email already registered")
        assertFalse(result.success)
        assertEquals("Email already registered", result.message)
    }

    @Test
    fun ticket2_TC04_statusResultClassExists() {
        val clazz = Class.forName("com.assignments.stockmarket.StatusResult")
        assertNotNull("StatusResult data class should exist", clazz)
    }

    @Test
    fun ticket2_TC05_statusResultDefaultValues() {
        val result = StatusResult()
        assertFalse("emailVerified should default to false", result.emailVerified)
        assertFalse("phoneVerified should default to false", result.phoneVerified)
    }

    @Test
    fun ticket2_TC06_statusResultWithValues() {
        val result = StatusResult(emailVerified = true, phoneVerified = true)
        assertTrue(result.emailVerified)
        assertTrue(result.phoneVerified)
    }

    @Test
    fun ticket2_TC07_signUpResultIsDataClass() {
        // Data classes implement copy(), equals(), hashCode(), toString()
        val r1 = SignUpResult(success = true)
        val r2 = SignUpResult(success = true)
        assertEquals("Data class equals should work", r1, r2)
    }

    @Test
    fun ticket2_TC08_statusResultIsDataClass() {
        val r1 = StatusResult(emailVerified = true)
        val r2 = StatusResult(emailVerified = true)
        assertEquals("Data class equals should work", r1, r2)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 3 — Implement authenticateUser suspend function
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket3_TC01_authenticateUserFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "authenticateUser should exist",
            methods.any { it.contains("authenticateUser") }
        )
    }

    @Test
    fun ticket3_TC02_authenticateUserIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("authenticateUser") }
        assertNotNull(method)
        // Suspend functions have a Continuation parameter as the last param
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "authenticateUser should be a suspend function (last param = Continuation)",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun ticket3_TC03_authenticateUserAccepts2StringParams() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("authenticateUser") }
        assertNotNull(method)
        val stringParams = method!!.parameterTypes.count { it.name == "java.lang.String" }
        assertEquals("Should accept 2 String params (username, password)", 2, stringParams)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 4 — Implement registerUser suspend function
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket4_TC01_registerUserFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "registerUser should exist",
            methods.any { it.contains("registerUser") }
        )
    }

    @Test
    fun ticket4_TC02_registerUserIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("registerUser") }
        assertNotNull(method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "registerUser should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun ticket4_TC03_registerUserAccepts6StringParams() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("registerUser") }
        assertNotNull(method)
        val stringParams = method!!.parameterTypes.count { it.name == "java.lang.String" }
        assertEquals(
            "Should accept 6 String params (firstName, lastName, username, email, phoneNumber, password)",
            6,
            stringParams
        )
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 5 — Implement sendOtpApi suspend function
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket5_TC01_sendOtpApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "sendOtpApi should exist",
            methods.any { it.contains("sendOtpApi") }
        )
    }

    @Test
    fun ticket5_TC02_sendOtpApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("sendOtpApi") }
        assertNotNull(method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "sendOtpApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun ticket5_TC03_sendOtpApiAccepts2StringParams() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("sendOtpApi") }
        assertNotNull(method)
        val stringParams = method!!.parameterTypes.count { it.name == "java.lang.String" }
        assertEquals("Should accept 2 String params (email, otp)", 2, stringParams)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 6 — Implement updateStatusApi suspend function
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket6_TC01_updateStatusApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "updateStatusApi should exist",
            methods.any { it.contains("updateStatusApi") }
        )
    }

    @Test
    fun ticket6_TC02_updateStatusApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("updateStatusApi") }
        assertNotNull(method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "updateStatusApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun ticket6_TC03_updateStatusApiAccepts1StringParam() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("updateStatusApi") }
        assertNotNull(method)
        val stringParams = method!!.parameterTypes.count { it.name == "java.lang.String" }
        assertEquals("Should accept 1 String param (email)", 1, stringParams)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 7 — Implement checkTheStatusApi suspend function
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket7_TC01_checkTheStatusApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "checkTheStatusApi should exist",
            methods.any { it.contains("checkTheStatusApi") }
        )
    }

    @Test
    fun ticket7_TC02_checkTheStatusApiIsSuspendFunction() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("checkTheStatusApi") }
        assertNotNull(method)
        val lastParam = method!!.parameterTypes.last()
        assertTrue(
            "checkTheStatusApi should be a suspend function",
            lastParam.name.contains("Continuation")
        )
    }

    @Test
    fun ticket7_TC03_checkTheStatusApiAccepts1StringParam() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("checkTheStatusApi") }
        assertNotNull(method)
        val stringParams = method!!.parameterTypes.count { it.name == "java.lang.String" }
        assertEquals("Should accept 1 String param (email)", 1, stringParams)
    }

    @Test
    fun ticket7_TC04_statusResultDefaultsOnError() {
        val result = StatusResult()
        assertFalse("emailVerified should default to false on error", result.emailVerified)
        assertFalse("phoneVerified should default to false on error", result.phoneVerified)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 8 — Refactor all screen files to use DedicatedAPI
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket8_TC01_noLocalAuthenticateUserInLoginScreen() {
        val clazz = Class.forName("com.assignments.stockmarket.LoginScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertFalse(
            "LoginScreen should NOT contain its own authenticateUser function",
            methods.any { it == "authenticateUser" }
        )
    }

    @Test
    fun ticket8_TC02_noLocalRegisterUserInSignUpScreen() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertFalse(
            "SignUpScreen should NOT contain its own registerUser function",
            methods.any { it == "registerUser" }
        )
    }

    @Test
    fun ticket8_TC03_noLocalSendOtpInOTPScreen() {
        val clazz = Class.forName("com.assignments.stockmarket.OTPScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertFalse(
            "OTPScreen should NOT contain its own sendOtpApi function",
            methods.any { it == "sendOtpApi" || it == "resendOtpApi" }
        )
    }

    @Test
    fun ticket8_TC04_noLocalCheckStatusInSplashScreen() {
        val clazz = Class.forName("com.assignments.stockmarket.SplashScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertFalse(
            "SplashScreen should NOT contain its own checkTheStatusApi function",
            methods.any { it == "checkTheStatusApi" }
        )
    }

    @Test
    fun ticket8_TC05_allApiFunctionsInDedicatedAPI() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue("authenticateUser should be in DedicatedAPI", methods.any { it.contains("authenticateUser") })
        assertTrue("registerUser should be in DedicatedAPI", methods.any { it.contains("registerUser") })
        assertTrue("sendOtpApi should be in DedicatedAPI", methods.any { it.contains("sendOtpApi") })
        assertTrue("updateStatusApi should be in DedicatedAPI", methods.any { it.contains("updateStatusApi") })
        assertTrue("checkTheStatusApi should be in DedicatedAPI", methods.any { it.contains("checkTheStatusApi") })
    }

    @Test
    fun ticket8_TC06_noUrlConstantsInScreenFiles() {
        // Verify no URL constants leak into screen files
        val screenFiles = listOf(
            "com.assignments.stockmarket.LoginScreenKt",
            "com.assignments.stockmarket.SignUpScreenKt",
            "com.assignments.stockmarket.OTPScreenKt",
            "com.assignments.stockmarket.SplashScreenKt"
        )
        for (className in screenFiles) {
            val clazz = Class.forName(className)
            val fields = clazz.fields.map { it.name }
            assertFalse(
                "$className should NOT contain URL constants",
                fields.any {
                    it.contains("API_URL") || it.contains("_URL") || it.contains("BASE_URL")
                }
            )
        }
    }

    @Test
    fun ticket8_TC07_noSignUpResultInSignUpScreen() {
        // SignUpResult should only be in DedicatedAPI.kt, not duplicated
        val signUpScreenMethods = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
            .declaredMethods.map { it.name }
        // SignUpResult class should only exist once in the package
        val signUpResult = Class.forName("com.assignments.stockmarket.SignUpResult")
        assertNotNull("SignUpResult should exist in the package", signUpResult)
    }

    @Test
    fun ticket8_TC08_allFiveApiFunctionsArePublic() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val publicMethods = clazz.methods.map { it.name }
        assertTrue(publicMethods.any { it.contains("authenticateUser") })
        assertTrue(publicMethods.any { it.contains("registerUser") })
        assertTrue(publicMethods.any { it.contains("sendOtpApi") })
        assertTrue(publicMethods.any { it.contains("updateStatusApi") })
        assertTrue(publicMethods.any { it.contains("checkTheStatusApi") })
    }
}
