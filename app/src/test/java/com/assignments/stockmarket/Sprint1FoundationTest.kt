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
 *   SPRINT 1 — Project Foundation & Configuration
 *   Test cases for tickets T1.1 through T1.10
 *   Validates: Gradle deps, colors, strings, drawables, raw assets,
 *              font certs, PoppinsFamily, Theme, API constants, MainActivity
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class Sprint1FoundationTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    // ════════════════════════════════════════════════════════════════════════
    //  T1.1 — Gradle Dependency Configuration
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_1_TC01_navigationComposeAvailable() {
        val clazz = Class.forName("androidx.navigation.compose.NavHostKt")
        assertNotNull("Navigation Compose library should be available", clazz)
    }

    @Test
    fun T1_1_TC02_paperDbAvailable() {
        val clazz = Class.forName("io.paperdb.Paper")
        assertNotNull("Paper DB library should be available", clazz)
    }

    @Test
    fun T1_1_TC03_lottieComposeAvailable() {
        val clazz = Class.forName("com.airbnb.lottie.compose.LottieAnimationKt")
        assertNotNull("Lottie Compose library should be available", clazz)
    }

    @Test
    fun T1_1_TC04_okhttpAvailable() {
        val clazz = Class.forName("okhttp3.OkHttpClient")
        assertNotNull("OkHttp library should be available", clazz)
    }

    @Test
    fun T1_1_TC05_coroutinesAvailable() {
        val clazz = Class.forName("kotlinx.coroutines.Dispatchers")
        assertNotNull("Kotlinx Coroutines should be available", clazz)
    }

    @Test
    fun T1_1_TC06_splashScreenApiAvailable() {
        val clazz = Class.forName("androidx.core.splashscreen.SplashScreen")
        assertNotNull("Core Splash Screen API should be available", clazz)
    }

    @Test
    fun T1_1_TC07_materialIconsExtendedAvailable() {
        val clazz = Class.forName("androidx.compose.material.icons.filled.ArrowForwardKt")
        assertNotNull("Material Icons Extended should be available", clazz)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.2 — Color Resources
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_2_TC01_screenBackgroundColorExists() {
        val resId = context.resources.getIdentifier("bg_primary", "color", context.packageName)
        assertTrue("bg_primary color should exist", resId != 0)
    }

    @Test
    fun T1_2_TC02_textFieldColorExists() {
        val resId = context.resources.getIdentifier("bg_text_field", "color", context.packageName)
        assertTrue("bg_text_field should exist", resId != 0)
    }

    @Test
    fun T1_2_TC03_textColorExists() {
        val resId = context.resources.getIdentifier("text_primary", "color", context.packageName)
        assertTrue("text_primary should exist", resId != 0)
    }

    @Test
    fun T1_2_TC04_buttonBackgroundColorExists() {
        val resId = context.resources.getIdentifier("bg_button_primary", "color", context.packageName)
        assertTrue("bg_button_primary should exist", resId != 0)
    }

    @Test
    fun T1_2_TC05_lightGreenTextColorExists() {
        val resId = context.resources.getIdentifier("text_success_light", "color", context.packageName)
        assertTrue("text_success_light should exist", resId != 0)
    }

    @Test
    fun T1_2_TC06_redTextColorExists() {
        val resId = context.resources.getIdentifier("text_error", "color", context.packageName)
        assertTrue("text_error should exist", resId != 0)
    }

    @Test
    fun T1_2_TC07_navBarBackgroundColorExists() {
        val resId = context.resources.getIdentifier("bg_nav_bar", "color", context.packageName)
        assertTrue("bg_nav_bar color should exist", resId != 0)
    }

    @Test
    fun T1_2_TC08_allTwentyColorsExist() {
        val colorNames = listOf(
            "bg_primary", "bg_text_field", "text_primary", "bg_button_primary",
            "white", "black", "text_success_light", "text_error", "text_success",
            "text_error_light", "text_secondary", "icon_tint_light",
            "text_link", "text_accent_blue", "text_accent_blue_light",
            "bg_button_secondary_light", "bg_button_secondary_dark", "bg_mpin",
            "bg_nav_bar", "icon_tint_light"
        )
        colorNames.forEach { name ->
            val resId = context.resources.getIdentifier(name, "color", context.packageName)
            assertTrue("Color '$name' should exist in colors.xml", resId != 0)
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.3 — String Resources
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_3_TC01_appNameStringExists() {
        val resId = context.resources.getIdentifier("app_name", "string", context.packageName)
        assertTrue("app_name string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC02_loginStringExists() {
        val resId = context.resources.getIdentifier("login", "string", context.packageName)
        assertTrue("login string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC03_usernameStringExists() {
        val resId = context.resources.getIdentifier("username", "string", context.packageName)
        assertTrue("username string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC04_passwordStringExists() {
        val resId = context.resources.getIdentifier("password", "string", context.packageName)
        assertTrue("password string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC05_signUpStringExists() {
        val resId = context.resources.getIdentifier("sign_up", "string", context.packageName)
        assertTrue("sign_up string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC06_otpStringExists() {
        val resId = context.resources.getIdentifier("otp", "string", context.packageName)
        assertTrue("otp string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC07_mpinStringExists() {
        val resId = context.resources.getIdentifier("mpin", "string", context.packageName)
        assertTrue("mpin string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC08_forgotPasswordStringExists() {
        val resId = context.resources.getIdentifier("forgot_password", "string", context.packageName)
        assertTrue("forgot_password string should exist", resId != 0)
    }

    @Test
    fun T1_3_TC09_allCriticalStringsExist() {
        val stringNames = listOf(
            "app_name", "login", "username", "password", "forget_password",
            "sign_up", "confirm_password", "mpin", "otp", "resend_now",
            "forgot_password", "enter_your_email_phone", "invalid_userid_or_password",
            "email", "first_name", "last_name", "phone_number",
            "signup_failed", "otp_sent_failed"
        )
        stringNames.forEach { name ->
            val resId = context.resources.getIdentifier(name, "string", context.packageName)
            assertTrue("String '$name' should exist in strings.xml", resId != 0)
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.4 — Drawable Resources
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_4_TC01_stockLogoDrawableExists() {
        val resId = context.resources.getIdentifier("ic_stock_logo", "drawable", context.packageName)
        assertTrue("ic_stock_logo drawable should exist", resId != 0)
    }

    @Test
    fun T1_4_TC02_biometricIconDrawableExists() {
        val resId = context.resources.getIdentifier("ic_biometric", "drawable", context.packageName)
        assertTrue("ic_biometric drawable should exist", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.5 — Raw Resources (Lottie & Audio)
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_5_TC01_confettiLottieRawResourceExists() {
        val resId = context.resources.getIdentifier("confetti", "raw", context.packageName)
        assertTrue("confetti Lottie raw resource should exist", resId != 0)
    }

    @Test
    fun T1_5_TC02_welcomeChimeRawResourceExists() {
        val resId = context.resources.getIdentifier("welcome_chime", "raw", context.packageName)
        assertTrue("welcome_chime audio raw resource should exist", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.6 — Google Fonts Certificate Configuration
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_6_TC01_fontCertsArrayResourceExists() {
        val resId = context.resources.getIdentifier(
            "com_google_android_gms_fonts_certs", "array", context.packageName
        )
        assertTrue("Google Fonts certificate array resource should exist", resId != 0)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.7 — PoppinsFamily Font Configuration
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_7_TC01_poppinsFamilyFieldExists() {
        val clazz = Class.forName("com.assignments.stockmarket.ui.theme.TypeKt")
        val field = clazz.declaredMethods.find { it.name.contains("getPoppinsFamily") }
        assertNotNull("PoppinsFamily getter should exist in Type.kt", field)
    }

    @Test
    fun T1_7_TC02_typographyFieldExists() {
        val clazz = Class.forName("com.assignments.stockmarket.ui.theme.TypeKt")
        val field = clazz.declaredMethods.find { it.name.contains("getTypography") }
        assertNotNull("Typography getter should exist in Type.kt", field)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.8 — Material3 Theme Configuration
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_8_TC01_themeComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.ui.theme.ThemeKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "StockMarketTheme composable should exist",
            methods.any { it.contains("StockMarketTheme") }
        )
    }

    @Test
    fun T1_8_TC02_colorKtClassExists() {
        val clazz = Class.forName("com.assignments.stockmarket.ui.theme.ColorKt")
        assertNotNull("Color.kt should exist in theme package", clazz)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.9 — API Endpoint Constants & Data Classes
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_9_TC01_loginApiUrlConstantExists() {
        assertEquals(
            "LOGIN_API_URL should point to /api/login",
            "https://system-project-api.onrender.com/api/login",
            LOGIN_API_URL
        )
    }

    @Test
    fun T1_9_TC02_signupApiUrlConstantExists() {
        assertEquals(
            "SIGNUP_API_URL should point to /api/signup",
            "https://system-project-api.onrender.com/api/signup",
            SIGNUP_API_URL
        )
    }

    @Test
    fun T1_9_TC03_sendOtpApiUrlConstantExists() {
        assertEquals(
            "SEND_OTP_API_URL should point to /api/sendotp",
            "https://system-project-api.onrender.com/api/sendotp",
            SEND_OTP_API_URL
        )
    }

    @Test
    fun T1_9_TC04_updateStatusApiUrlConstantExists() {
        assertEquals(
            "UPDATE_STATUS_API_URL should point to /api/updatestatus",
            "https://system-project-api.onrender.com/api/updatestatus",
            UPDATE_STATUS_API_URL
        )
    }

    @Test
    fun T1_9_TC05_checkStatusApiUrlConstantExists() {
        assertEquals(
            "CHECK_STATUS_API_URL should point to /api/checkthestatus",
            "https://system-project-api.onrender.com/api/checkthestatus",
            CHECK_STATUS_API_URL
        )
    }

    @Test
    fun T1_9_TC06_forgotPasswordApiUrlConstantExists() {
        assertEquals(
            "FORGOT_PASSWORD_API_URL should point to /api/forgotpassword",
            "https://system-project-api.onrender.com/api/forgotpassword",
            FORGOT_PASSWORD_API_URL
        )
    }

    @Test
    fun T1_9_TC07_resetPasswordApiUrlConstantExists() {
        assertEquals(
            "RESET_PASSWORD_API_URL should point to /api/resetpassword",
            "https://system-project-api.onrender.com/api/resetpassword",
            RESET_PASSWORD_API_URL
        )
    }

    @Test
    fun T1_9_TC08_companiesApiUrlConstantExists() {
        assertEquals(
            "COMPANIES_API_URL should point to /api/companies",
            "https://system-project-api.onrender.com/api/companies",
            COMPANIES_API_URL
        )
    }

    @Test
    fun T1_9_TC09_signUpResultDataClassExists() {
        val result = SignUpResult(success = true, message = "OK")
        assertTrue("SignUpResult.success should be true", result.success)
        assertEquals("SignUpResult.message should be OK", "OK", result.message)
    }

    @Test
    fun T1_9_TC10_signUpResultDefaultMessageIsNull() {
        val result = SignUpResult(success = false)
        assertNull("SignUpResult.message default should be null", result.message)
    }

    @Test
    fun T1_9_TC11_statusResultDataClassExists() {
        val result = StatusResult(emailVerified = true, phoneVerified = false)
        assertTrue("StatusResult.emailVerified should be true", result.emailVerified)
        assertFalse("StatusResult.phoneVerified should be false", result.phoneVerified)
    }

    @Test
    fun T1_9_TC12_statusResultDefaultsAreFalse() {
        val result = StatusResult()
        assertFalse("StatusResult.emailVerified default should be false", result.emailVerified)
        assertFalse("StatusResult.phoneVerified default should be false", result.phoneVerified)
    }

    @Test
    fun T1_9_TC13_forgotPasswordResultDataClassExists() {
        val result = ForgotPasswordResult(success = true, otp = "123456", message = "Sent")
        assertTrue("ForgotPasswordResult.success should be true", result.success)
        assertEquals("ForgotPasswordResult.otp should be 123456", "123456", result.otp)
        assertEquals("ForgotPasswordResult.message should be Sent", "Sent", result.message)
    }

    @Test
    fun T1_9_TC14_forgotPasswordResultDefaultsAreNull() {
        val result = ForgotPasswordResult(success = false)
        assertNull("ForgotPasswordResult.otp default should be null", result.otp)
        assertNull("ForgotPasswordResult.message default should be null", result.message)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T1.10 — MainActivity Base Shell
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T1_10_TC01_mainActivityClassExists() {
        val clazz = Class.forName("com.assignments.stockmarket.MainActivity")
        assertNotNull("MainActivity class should exist", clazz)
    }

    @Test
    fun T1_10_TC02_mainActivityExtendsAppCompatActivity() {
        val clazz = Class.forName("com.assignments.stockmarket.MainActivity")
        val superClass = clazz.superclass?.name ?: ""
        assertTrue(
            "MainActivity should extend AppCompatActivity",
            superClass.contains("AppCompatActivity")
        )
    }

    @Test
    fun T1_10_TC03_mainActivityHasOnCreate() {
        val clazz = Class.forName("com.assignments.stockmarket.MainActivity")
        val onCreate = clazz.declaredMethods.find { it.name == "onCreate" }
        assertNotNull("MainActivity should have onCreate method", onCreate)
    }
}

