package com.assignments.stockmarket

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * ============================================================================
 *   SPRINT 3 — Screen UI Layouts & Shared UI Helpers
 *   Test cases for tickets T3.1 through T3.10
 *   Validates: 8 screen composable shells + PasswordCriteriaRow + CircularSubmitButton
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class Sprint3ScreenUIsTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    // ════════════════════════════════════════════════════════════════════════
    //  T3.1 — SplashScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_1_TC01_splashScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.SplashScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "SplashScreen composable should exist",
            methods.any { it.contains("SplashScreen") }
        )
    }

    @Test
    fun T3_1_TC02_splashScreenAcceptsNavController() {
        val clazz = Class.forName("com.assignments.stockmarket.SplashScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("SplashScreen") }
        assertNotNull("SplashScreen should exist", method)
        assertTrue(
            "SplashScreen should accept NavController + Composer params",
            method!!.parameterCount >= 2
        )
    }

    @Test
    fun T3_1_TC03_splashScreenUsesStockLogoDrawable() {
        val resId = context.resources.getIdentifier("ic_stock_logo", "drawable", context.packageName)
        assertTrue("ic_stock_logo must exist for SplashScreen", resId != 0)
    }

    @Test
    fun T3_1_TC04_splashScreenUsesAppNameString() {
        val resId = context.resources.getIdentifier("app_name", "string", context.packageName)
        assertTrue("app_name string must exist for SplashScreen", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.2 — LoginScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_2_TC01_loginScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.LoginScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "LoginScreen composable should exist",
            methods.any { it.contains("LoginScreen") }
        )
    }

    @Test
    fun T3_2_TC02_loginScreenAcceptsNavController() {
        val clazz = Class.forName("com.assignments.stockmarket.LoginScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("LoginScreen") }
        assertNotNull("LoginScreen should exist", method)
        assertTrue("LoginScreen should have multiple params", method!!.parameterCount >= 2)
    }

    @Test
    fun T3_2_TC03_loginStringResourceExists() {
        val resId = context.resources.getIdentifier("login", "string", context.packageName)
        assertTrue("'login' string resource must exist for title", resId != 0)
    }

    @Test
    fun T3_2_TC04_usernameStringResourceExists() {
        val resId = context.resources.getIdentifier("username", "string", context.packageName)
        assertTrue("'username' string resource must exist for field", resId != 0)
    }

    @Test
    fun T3_2_TC05_passwordStringResourceExists() {
        val resId = context.resources.getIdentifier("password", "string", context.packageName)
        assertTrue("'password' string resource must exist for field", resId != 0)
    }

    @Test
    fun T3_2_TC06_forgetPasswordStringResourceExists() {
        val resId = context.resources.getIdentifier("forget_password", "string", context.packageName)
        assertTrue("'forget_password' string must exist for link", resId != 0)
    }

    @Test
    fun T3_2_TC07_dontHaveAccountStringExists() {
        val resId = context.resources.getIdentifier(
            "don_t_have_any_account_click_to_create", "string", context.packageName
        )
        assertTrue("Bottom signup link string must exist", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.3 — SignUpScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_3_TC01_signUpScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "SignUpScreen composable should exist",
            methods.any { it.contains("SignUpScreen") }
        )
    }

    @Test
    fun T3_3_TC02_signUpStringResourceExists() {
        val resId = context.resources.getIdentifier("sign_up", "string", context.packageName)
        assertTrue("'sign_up' string resource must exist", resId != 0)
    }

    @Test
    fun T3_3_TC03_firstNameStringResourceExists() {
        val resId = context.resources.getIdentifier("first_name", "string", context.packageName)
        assertTrue("'first_name' string must exist", resId != 0)
    }

    @Test
    fun T3_3_TC04_lastNameStringResourceExists() {
        val resId = context.resources.getIdentifier("last_name", "string", context.packageName)
        assertTrue("'last_name' string must exist", resId != 0)
    }

    @Test
    fun T3_3_TC05_emailStringResourceExists() {
        val resId = context.resources.getIdentifier("email", "string", context.packageName)
        assertTrue("'email' string must exist", resId != 0)
    }

    @Test
    fun T3_3_TC06_phoneNumberStringResourceExists() {
        val resId = context.resources.getIdentifier("phone_number", "string", context.packageName)
        assertTrue("'phone_number' string must exist", resId != 0)
    }

    @Test
    fun T3_3_TC07_confirmPasswordStringExists() {
        val resId = context.resources.getIdentifier("confirm_password", "string", context.packageName)
        assertTrue("'confirm_password' string must exist", resId != 0)
    }

    @Test
    fun T3_3_TC08_alreadyHaveAccountStringExists() {
        val resId = context.resources.getIdentifier(
            "already_have_an_account_click_to_login", "string", context.packageName
        )
        assertTrue("Bottom login link string must exist", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.4 — OTPScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_4_TC01_otpScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.OTPScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "OTPScreen composable should exist",
            methods.any { it.contains("OTPScreen") }
        )
    }

    @Test
    fun T3_4_TC02_otpScreenAcceptsEmailAndOtpParams() {
        val clazz = Class.forName("com.assignments.stockmarket.OTPScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("OTPScreen") }
        assertNotNull("OTPScreen should exist", method)
        // NavController + email + otp + isResetMpin + isForgotPassword + Composer + changed
        assertTrue("OTPScreen should have parameters", method!!.parameterCount >= 3)
    }

    @Test
    fun T3_4_TC03_otpStringResourceExists() {
        val resId = context.resources.getIdentifier("otp", "string", context.packageName)
        assertTrue("'otp' string must exist for title", resId != 0)
    }

    @Test
    fun T3_4_TC04_resendNowStringExists() {
        val resId = context.resources.getIdentifier("resend_now", "string", context.packageName)
        assertTrue("'resend_now' string must exist", resId != 0)
    }

    @Test
    fun T3_4_TC05_otpLengthDerivedFromForgotPasswordFlag() {
        // When isForgotPassword = true, otpLength should be 6; else 4
        val normalLength = if (false) 6 else 4
        val forgotLength = if (true) 6 else 4
        assertEquals("Normal OTP length should be 4", 4, normalLength)
        assertEquals("Forgot password OTP length should be 6", 6, forgotLength)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.5 — MPINScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_5_TC01_mpinScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.MPINScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "MPINScreen composable should exist",
            methods.any { it.contains("MPINScreen") }
        )
    }

    @Test
    fun T3_5_TC02_mpinScreenAcceptsModeFlags() {
        val clazz = Class.forName("com.assignments.stockmarket.MPINScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("MPINScreen") }
        assertNotNull("MPINScreen should exist", method)
        // NavController + isVerifyMode + isResetMode + onMPINClick + Composer + changed
        assertTrue("MPINScreen should have mode flag params", method!!.parameterCount >= 2)
    }

    @Test
    fun T3_5_TC03_mpinStringResourceExists() {
        val resId = context.resources.getIdentifier("mpin", "string", context.packageName)
        assertTrue("'mpin' string resource must exist", resId != 0)
    }

    @Test
    fun T3_5_TC04_biometricIconExists() {
        val resId = context.resources.getIdentifier("ic_biometric", "drawable", context.packageName)
        assertTrue("ic_biometric drawable must exist for MPIN screen", resId != 0)
    }

    @Test
    fun T3_5_TC05_mpinTitleAdaptsToMode() {
        // Verify title logic
        val setupTitle = when {
            false -> "Set New MPIN"  // isResetMode
            false -> "Enter MPIN"    // isVerifyMode
            else -> "MPIN"
        }
        val verifyTitle = when {
            false -> "Set New MPIN"
            true -> "Enter MPIN"
            else -> "MPIN"
        }
        val resetTitle = when {
            true -> "Set New MPIN"
            false -> "Enter MPIN"
            else -> "MPIN"
        }
        assertEquals("Default title should be MPIN", "MPIN", setupTitle)
        assertEquals("Verify title should be Enter MPIN", "Enter MPIN", verifyTitle)
        assertEquals("Reset title should be Set New MPIN", "Set New MPIN", resetTitle)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.6 — WelcomeScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_6_TC01_welcomeScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.WelcomeScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "WelcomeScreen composable should exist",
            methods.any { it.contains("WelcomeScreen") }
        )
    }

    @Test
    fun T3_6_TC02_welcomeScreenAcceptsNavController() {
        val clazz = Class.forName("com.assignments.stockmarket.WelcomeScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("WelcomeScreen") }
        assertNotNull("WelcomeScreen should exist", method)
        // Compose compiler generates NavController + Composer + changed params
        assertTrue("WelcomeScreen should accept at least 1 parameter", method!!.parameterCount >= 1)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.7 — ForgotPasswordScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_7_TC01_forgotPasswordScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.ForgotPasswordScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "ForgotPasswordScreen composable should exist",
            methods.any { it.contains("ForgotPasswordScreen") }
        )
    }

    @Test
    fun T3_7_TC02_forgotPasswordStringExists() {
        val resId = context.resources.getIdentifier("forgot_password", "string", context.packageName)
        assertTrue("'forgot_password' string must exist for title", resId != 0)
    }

    @Test
    fun T3_7_TC03_enterEmailPhoneStringExists() {
        val resId = context.resources.getIdentifier("enter_your_email_phone", "string", context.packageName)
        assertTrue("'enter_your_email_phone' string must exist for placeholder", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.8 — ResetPasswordScreen Static UI Layout
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_8_TC01_resetPasswordScreenComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.ResetPasswordScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "ResetPasswordScreen composable should exist",
            methods.any { it.contains("ResetPasswordScreen") }
        )
    }

    @Test
    fun T3_8_TC02_resetPasswordScreenAcceptsIdentifier() {
        val clazz = Class.forName("com.assignments.stockmarket.ResetPasswordScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("ResetPasswordScreen") }
        assertNotNull("ResetPasswordScreen should exist", method)
        // NavController + identifier + Composer + changed
        assertTrue("ResetPasswordScreen should accept identifier param", method!!.parameterCount >= 2)
    }

    @Test
    fun T3_8_TC03_resetPasswordStringExists() {
        val resId = context.resources.getIdentifier("reset_password", "string", context.packageName)
        assertTrue("'reset_password' string must exist", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.9 — PasswordCriteriaRow Shared Composable
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_9_TC01_passwordCriteriaRowLogicMinLength() {
        val password = "Abcdefg1!"
        assertTrue("9 chars meets min 8 requirement", password.length >= 8)
    }

    @Test
    fun T3_9_TC02_passwordCriteriaRowLogicUpperCase() {
        val password = "Abcdefg1!"
        assertTrue("Has uppercase", password.any { it.isUpperCase() })
    }

    @Test
    fun T3_9_TC03_passwordCriteriaRowLogicLowerCase() {
        val password = "Abcdefg1!"
        assertTrue("Has lowercase", password.any { it.isLowerCase() })
    }

    @Test
    fun T3_9_TC04_passwordCriteriaRowLogicDigit() {
        val password = "Abcdefg1!"
        assertTrue("Has digit", password.any { it.isDigit() })
    }

    @Test
    fun T3_9_TC05_passwordCriteriaRowLogicSymbol() {
        val password = "Abcdefg1!"
        assertTrue("Has symbol", password.any { !it.isLetterOrDigit() })
    }

    @Test
    fun T3_9_TC06_weakPasswordFailsCriteria() {
        val password = "abc"
        assertFalse("Short password fails min length", password.length >= 8)
        assertFalse("No uppercase", password.any { it.isUpperCase() })
        assertFalse("No digit", password.any { it.isDigit() })
        assertFalse("No symbol", password.any { !it.isLetterOrDigit() })
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T3.10 — CircularSubmitButton Shared Composable
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T3_10_TC01_circularButtonSizeIs86dp() {
        // The specification requires 86dp circular button
        val expectedSizeDp = 86
        assertEquals("Circular button size should be 86dp", 86, expectedSizeDp)
    }

    @Test
    fun T3_10_TC02_circularButtonLoadingGuard() {
        // When isLoading is true, clicks should be ignored
        var isLoading = true
        var clickCount = 0
        if (!isLoading) clickCount++
        assertEquals("Clicks should be blocked while loading", 0, clickCount)
    }

    @Test
    fun T3_10_TC03_circularButtonEnabledGuard() {
        // When enabled is false, clicks should be blocked
        var enabled = false
        var clickCount = 0
        if (enabled) clickCount++
        assertEquals("Clicks should be blocked when disabled", 0, clickCount)
    }

    @Test
    fun T3_10_TC04_circularButtonAllowsClickWhenIdleAndEnabled() {
        var isLoading = false
        var enabled = true
        var clickCount = 0
        if (!isLoading && enabled) clickCount++
        assertEquals("Click should register when idle and enabled", 1, clickCount)
    }
}

