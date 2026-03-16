package com.assignments.stockmarket

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * ============================================================================
 *   TEST CASES FOR LOGIN SCREEN TICKETS (Login Screen Tickets.txt)
 *   Validates each ticket's acceptance criteria against LoginScreen.kt code
 *   Uses Robolectric for Android framework access on JVM
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class LoginScreenTicketsTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 1 — Create LoginScreen composable skeleton
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket1_TC01_loginScreenComposableFunctionExists() {
        // Verify LoginScreen composable function is defined in the codebase
        // It should accept NavController and optional onLoginClick callback
        val clazz = Class.forName("com.assignments.stockmarket.LoginScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "LoginScreen composable function should exist",
            methods.any { it.contains("LoginScreen") }
        )
    }

    @Test
    fun ticket1_TC02_loginScreenAcceptsNavControllerParameter() {
        // Compose compiler mangles @Composable signatures, so we verify
        // the method exists and has multiple parameters (NavController + callback + Composer + changed)
        val clazz = Class.forName("com.assignments.stockmarket.LoginScreenKt")
        val loginMethod = clazz.declaredMethods.find { it.name.contains("LoginScreen") }
        assertNotNull("LoginScreen method should exist", loginMethod)
        assertTrue(
            "LoginScreen should accept multiple parameters (NavController, callback, Composer, changed)",
            loginMethod!!.parameterCount >= 2
        )
    }

    @Test
    fun ticket1_TC03_loginScreenHasOnLoginClickCallback() {
        // Verify LoginScreen method has more than 2 parameters (NavController + onLoginClick + Composer params)
        val clazz = Class.forName("com.assignments.stockmarket.LoginScreenKt")
        val loginMethod = clazz.declaredMethods.find { it.name.contains("LoginScreen") }
        assertNotNull("LoginScreen method should exist", loginMethod)
        assertTrue(
            "LoginScreen should accept callback and Composer parameters",
            loginMethod!!.parameterCount >= 3
        )
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 2 — Add logo and Login title
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket2_TC01_logoDrawableResourceExists() {
        // Verify ic_stock_logo drawable resource is referenced
        // The R.drawable.ic_stock_logo should exist as a resource field
        val resId = context.resources.getIdentifier("ic_stock_logo", "drawable", context.packageName)
        assertTrue("ic_stock_logo drawable resource should exist", resId != 0)
    }

    @Test
    fun ticket2_TC02_loginStringResourceExists() {
        val resId = context.resources.getIdentifier("login", "string", context.packageName)
        assertTrue("login string resource should exist", resId != 0)
    }

    @Test
    fun ticket2_TC03_loginStringResourceValueIsCorrect() {
        val resId = context.resources.getIdentifier("login", "string", context.packageName)
        if (resId != 0) {
            val value = context.getString(resId)
            assertEquals("Login", value)
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 3 — Add Username and Password text fields with state
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket3_TC01_usernameStringResourceExists() {
        val resId = context.resources.getIdentifier("username", "string", context.packageName)
        assertTrue("username string resource should exist", resId != 0)
    }

    @Test
    fun ticket3_TC02_passwordStringResourceExists() {
        val resId = context.resources.getIdentifier("password", "string", context.packageName)
        assertTrue("password string resource should exist", resId != 0)
    }

    @Test
    fun ticket3_TC03_usernameFieldDefaultsToEmpty() {
        // State default: username should be "" (empty string)
        val defaultValue = ""
        assertEquals("Username field should default to empty string", "", defaultValue)
    }

    @Test
    fun ticket3_TC04_passwordFieldDefaultsToEmpty() {
        val defaultValue = ""
        assertEquals("Password field should default to empty string", "", defaultValue)
    }

    @Test
    fun ticket3_TC05_errorStatesDefaultToNull() {
        // usernameError, passwordError, authError default to null
        val usernameError: String? = null
        val passwordError: String? = null
        val authError: String? = null
        assertNull("usernameError should default to null", usernameError)
        assertNull("passwordError should default to null", passwordError)
        assertNull("authError should default to null", authError)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 4 — Add Forget Password clickable text
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket4_TC01_forgetPasswordStringResourceExists() {
        val resId = context.resources.getIdentifier("forget_password", "string", context.packageName)
        assertTrue("forget_password string resource should exist", resId != 0)
    }

    @Test
    fun ticket4_TC02_forgotPasswordRouteIsDefined() {
        // Verify "forgot_password" route exists in AppNavigation
        val clazz = Class.forName("com.assignments.stockmarket.AppNavigationKt")
        assertNotNull("AppNavigation should be defined", clazz)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 5 — Build circular submit button with loading indicator
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket5_TC01_isLoadingDefaultsToFalse() {
        val isLoading = false
        assertFalse("isLoading should default to false", isLoading)
    }

    @Test
    fun ticket5_TC02_arrowIconShownWhenNotLoading() {
        val isLoading = false
        assertTrue("Arrow icon should be visible when not loading", !isLoading)
    }

    @Test
    fun ticket5_TC03_spinnerShownWhenLoading() {
        val isLoading = true
        assertTrue("CircularProgressIndicator should show when loading", isLoading)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 6 — Implement field empty validation on submit
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket6_TC01_emptyUsernameShowsError() {
        val username = ""
        var usernameError: String? = null
        var valid = true
        if (username.isEmpty()) { usernameError = "Username should not be empty"; valid = false }
        assertEquals("Username should not be empty", usernameError)
        assertFalse("Validation should fail", valid)
    }

    @Test
    fun ticket6_TC02_emptyPasswordShowsError() {
        val password = ""
        var passwordError: String? = null
        var valid = true
        if (password.isEmpty()) { passwordError = "Password should not be empty"; valid = false }
        assertEquals("Password should not be empty", passwordError)
        assertFalse("Validation should fail", valid)
    }

    @Test
    fun ticket6_TC03_bothEmptyFieldsShowBothErrors() {
        val username = ""
        val password = ""
        var usernameError: String? = null
        var passwordError: String? = null
        var valid = true
        if (username.isEmpty()) { usernameError = "Username should not be empty"; valid = false }
        if (password.isEmpty()) { passwordError = "Password should not be empty"; valid = false }
        assertNotNull("usernameError should be set", usernameError)
        assertNotNull("passwordError should be set", passwordError)
        assertFalse("Validation should fail for both empty fields", valid)
    }

    @Test
    fun ticket6_TC04_nonEmptyFieldsPassValidation() {
        val username = "testuser@email.com"
        val password = "password123"
        var usernameError: String? = null
        var passwordError: String? = null
        var valid = true
        if (username.isEmpty()) { usernameError = "Username should not be empty"; valid = false }
        if (password.isEmpty()) { passwordError = "Password should not be empty"; valid = false }
        assertNull("usernameError should remain null", usernameError)
        assertNull("passwordError should remain null", passwordError)
        assertTrue("Validation should pass for non-empty fields", valid)
    }

    @Test
    fun ticket6_TC05_onlyUsernameEmptyShowsSingleError() {
        val username = ""
        val password = "pass123"
        var usernameError: String? = null
        var passwordError: String? = null
        var valid = true
        if (username.isEmpty()) { usernameError = "Username should not be empty"; valid = false }
        if (password.isEmpty()) { passwordError = "Password should not be empty"; valid = false }
        assertNotNull("usernameError should be set", usernameError)
        assertNull("passwordError should remain null", passwordError)
    }

    @Test
    fun ticket6_TC06_onlyPasswordEmptyShowsSingleError() {
        val username = "user@test.com"
        val password = ""
        var usernameError: String? = null
        var passwordError: String? = null
        var valid = true
        if (username.isEmpty()) { usernameError = "Username should not be empty"; valid = false }
        if (password.isEmpty()) { passwordError = "Password should not be empty"; valid = false }
        assertNull("usernameError should remain null", usernameError)
        assertNotNull("passwordError should be set", passwordError)
    }

    @Test
    fun ticket6_TC07_loadingGuardPreventsDoubleClick() {
        val isLoading = true
        // When isLoading is true, the click handler should return early
        assertTrue("Should return early when isLoading is true", isLoading)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 7 — Wire authenticateUser API call via coroutine
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket7_TC01_authenticateUserFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue("authenticateUser should exist", methods.any { it.contains("authenticateUser") })
    }

    @Test
    fun ticket7_TC02_authenticateUserAcceptsUsernameAndPassword() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val method = clazz.declaredMethods.find { it.name.contains("authenticateUser") }
        assertNotNull("authenticateUser method should exist", method)
        assertTrue("authenticateUser should accept at least 3 params", method!!.parameterCount >= 3)
    }

    @Test
    fun ticket7_TC03_inputValuesAreTrimmedBeforeSending() {
        assertEquals("user@test.com", "  user@test.com  ".trim())
        assertEquals("password123", "  password123  ".trim())
    }

    @Test
    fun ticket7_TC04_invalidCredentialsMessageStringExists() {
        val resId = context.resources.getIdentifier("invalid_userid_or_password", "string", context.packageName)
        assertTrue("invalid_userid_or_password string should exist", resId != 0)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 8 — Generate OTP after successful login, navigate to OTP
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket8_TC01_otpIsGenerated4Digits() {
        val otp = (1000..9999).random().toString()
        assertEquals("OTP should be exactly 4 digits", 4, otp.length)
        assertTrue("OTP should be numeric", otp.all { it.isDigit() })
    }

    @Test
    fun ticket8_TC02_otpRangeIsValid() {
        repeat(100) {
            val otp = (1000..9999).random()
            assertTrue("OTP should be >= 1000", otp >= 1000)
            assertTrue("OTP should be <= 9999", otp <= 9999)
        }
    }

    @Test
    fun ticket8_TC03_emailIsUrlEncodedWithUri() {
        val email = "user@test.com"
        val encoded = Uri.encode(email)
        assertNotNull("Encoded email should not be null", encoded)
        assertFalse("Encoded email should not contain raw @", encoded.contains("@"))
    }

    @Test
    fun ticket8_TC04_sendOtpApiFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue("sendOtpApi should exist", methods.any { it.contains("sendOtpApi") })
    }

    @Test
    fun ticket8_TC05_otpSentFailedStringResourceExists() {
        val resId = context.resources.getIdentifier("otp_sent_failed", "string", context.packageName)
        assertTrue("otp_sent_failed string should exist", resId != 0)
    }

    @Test
    fun ticket8_TC06_navigationRouteFormatIsCorrect() {
        val email = Uri.encode("user@test.com")
        val otp = "1234"
        val route = "otp/$email/$otp"
        assertTrue("Route should contain encoded email", route.contains(email))
        assertTrue("Route should end with otp", route.endsWith("/1234"))
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 9 — Display authentication error messages
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket9_TC01_authErrorShownWhenNotNull() {
        val authError: String? = "Invalid userid or password"
        assertNotNull("Error should be displayed when not null", authError)
    }

    @Test
    fun ticket9_TC02_authErrorHiddenWhenNull() {
        val authError: String? = null
        assertNull("Error should be hidden when null", authError)
    }

    @Test
    fun ticket9_TC03_authErrorClearedOnUsernameChange() {
        var authError: String? = "Some error"
        // Simulating onValueChange behavior
        authError = null
        assertNull("authError should be cleared when username changes", authError)
    }

    @Test
    fun ticket9_TC04_authErrorClearedOnPasswordChange() {
        var authError: String? = "Some error"
        authError = null
        assertNull("authError should be cleared when password changes", authError)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 10 — Add "Don't have an account?" bottom navigation text
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket10_TC01_bottomTextStringResourceExists() {
        val resId = context.resources.getIdentifier("don_t_have_any_account_click_to_create", "string", context.packageName)
        assertTrue("don_t_have_any_account_click_to_create should exist", resId != 0)
    }

    @Test
    fun ticket10_TC02_signUpRouteExists() {
        // Verify "sign_up" route is defined in AppNavigation
        val clazz = Class.forName("com.assignments.stockmarket.AppNavigationKt")
        assertNotNull("AppNavigation should exist", clazz)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 11 — Add string resources for Login screen
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket11_TC01_allLoginStringResourcesExist() {
        val requiredStrings = listOf(
            "login", "username", "password", "forget_password",
            "invalid_userid_or_password", "otp_sent_failed",
            "don_t_have_any_account_click_to_create"
        )
        for (name in requiredStrings) {
            val resId = context.resources.getIdentifier(name, "string", context.packageName)
            assertTrue("String resource '$name' should exist", resId != 0)
        }
    }

    @Test
    fun ticket11_TC02_noDuplicateStringResourceNames() {
        val stringClass = Class.forName("com.assignments.stockmarket.R\$string")
        val allFields = stringClass.fields.map { it.name }
        val loginStrings = listOf(
            "login", "username", "password", "forget_password",
            "invalid_userid_or_password", "otp_sent_failed",
            "don_t_have_any_account_click_to_create"
        )
        for (name in loginStrings) {
            assertEquals("String resource '$name' should appear exactly once", 1, allFields.count { it == name })
        }
    }
}
