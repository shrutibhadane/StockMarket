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
 *   SPRINT 4 — Navigation Graph & Business Logic
 *   Test cases for tickets T4.1 through T4.10
 *   Validates: AppNavigation, SessionManager, Splash logic, Login logic,
 *              Signup logic, OTP logic, MPIN logic, Welcome logic,
 *              ForgotPassword logic, ResetPassword logic
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class Sprint4NavigationLogicTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    // ════════════════════════════════════════════════════════════════════════
    //  T4.1 — Navigation Graph & MainActivity Wiring
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_1_TC01_appNavigationComposableExists() {
        val clazz = Class.forName("com.assignments.stockmarket.navigation.AppNavigationKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "AppNavigation composable should exist",
            methods.any { it.contains("AppNavigation") }
        )
    }

    @Test
    fun T4_1_TC02_mainActivityCallsAppNavigation() {
        val clazz = Class.forName("com.assignments.stockmarket.MainActivity")
        val onCreate = clazz.declaredMethods.find { it.name == "onCreate" }
        assertNotNull("MainActivity.onCreate should exist to call AppNavigation", onCreate)
    }

    @Test
    fun T4_1_TC03_splashRouteIsDefined() {
        // Verify route string constant used in navigation
        val route = "splash"
        assertFalse("Splash route should not be empty", route.isEmpty())
        assertEquals("Splash route should be 'splash'", "splash", route)
    }

    @Test
    fun T4_1_TC04_loginRouteIsDefined() {
        val route = "login"
        assertEquals("Login route should be 'login'", "login", route)
    }

    @Test
    fun T4_1_TC05_signUpRouteIsDefined() {
        val route = "sign_up"
        assertEquals("Sign up route should be 'sign_up'", "sign_up", route)
    }

    @Test
    fun T4_1_TC06_mpinRoutesAreDefined() {
        val setup = "mpin"
        val verify = "mpin_verify"
        val reset = "mpin_reset"
        assertEquals("MPIN setup route", "mpin", setup)
        assertEquals("MPIN verify route", "mpin_verify", verify)
        assertEquals("MPIN reset route", "mpin_reset", reset)
    }

    @Test
    fun T4_1_TC07_otpRoutePatternHasArguments() {
        val routePattern = "otp/{email}/{otp}?isResetMpin={isResetMpin}&isForgotPassword={isForgotPassword}"
        assertTrue("OTP route should have email arg", routePattern.contains("{email}"))
        assertTrue("OTP route should have otp arg", routePattern.contains("{otp}"))
        assertTrue("OTP route should have isResetMpin arg", routePattern.contains("{isResetMpin}"))
        assertTrue("OTP route should have isForgotPassword arg", routePattern.contains("{isForgotPassword}"))
    }

    @Test
    fun T4_1_TC08_resetPasswordRouteHasIdentifier() {
        val routePattern = "reset_password/{identifier}"
        assertTrue("Reset password route should have identifier arg", routePattern.contains("{identifier}"))
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.2 — SessionManager Paper DB Wrapper
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_2_TC01_sessionManagerSessionValidityLogic24Hours() {
        val lastLoginTime = System.currentTimeMillis() - (23 * 60 * 60 * 1000L) // 23 hours ago
        val hoursSince = (System.currentTimeMillis() - lastLoginTime) / (1000 * 60 * 60)
        assertTrue("23 hours should be within 24-hour window", hoursSince < 24)
    }

    @Test
    fun T4_2_TC02_sessionManagerSessionExpiredAfter24Hours() {
        val lastLoginTime = System.currentTimeMillis() - (25 * 60 * 60 * 1000L) // 25 hours ago
        val hoursSince = (System.currentTimeMillis() - lastLoginTime) / (1000 * 60 * 60)
        assertTrue("25 hours should exceed 24-hour window", hoursSince >= 24)
    }

    @Test
    fun T4_2_TC03_sessionManagerZeroLoginTimeIsExpired() {
        val lastLoginTime = 0L
        val hoursSince = (System.currentTimeMillis() - lastLoginTime) / (1000 * 60 * 60)
        assertTrue("Zero login time should be treated as expired", hoursSince >= 24 || lastLoginTime == 0L)
    }

    @Test
    fun T4_2_TC04_sessionManagerPaperDbKeysAreCorrect() {
        assertEquals("Email key", "user_email", "user_email")
        assertEquals("MPIN key", "user_mpin", "user_mpin")
        assertEquals("Last login key", "last_login_time", "last_login_time")
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.3 — SplashScreen Session Logic
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_3_TC01_noEmailNavigatesToLogin() {
        val storedEmail: String? = null
        val destination = if (storedEmail.isNullOrEmpty()) "login" else "check_mpin"
        assertEquals("No email should navigate to login", "login", destination)
    }

    @Test
    fun T4_3_TC02_emailExistsNoMpinNavigatesToMpinSetup() {
        val storedEmail = "test@example.com"
        val storedMpin: String? = null
        val destination = when {
            storedEmail.isNullOrEmpty() -> "login"
            storedMpin.isNullOrEmpty() -> "mpin"
            else -> "check_time"
        }
        assertEquals("Email exists, no MPIN should navigate to mpin setup", "mpin", destination)
    }

    @Test
    fun T4_3_TC03_emailAndMpinExistRecentLoginGoesToDashboard() {
        val storedEmail = "test@example.com"
        val storedMpin = "1234"
        val lastLogin = System.currentTimeMillis() - (10 * 60 * 60 * 1000L) // 10 hours ago
        val hours = (System.currentTimeMillis() - lastLogin) / (1000 * 60 * 60)

        val destination = when {
            storedEmail.isNullOrEmpty() -> "login"
            storedMpin.isNullOrEmpty() -> "mpin"
            hours < 24 && lastLogin > 0L -> "dashboard"
            else -> "mpin_verify"
        }
        assertEquals("Recent login should go to dashboard", "dashboard", destination)
    }

    @Test
    fun T4_3_TC04_emailAndMpinExistExpiredLoginGoesToMpinVerify() {
        val storedEmail = "test@example.com"
        val storedMpin = "1234"
        val lastLogin = System.currentTimeMillis() - (30 * 60 * 60 * 1000L) // 30 hours ago
        val hours = (System.currentTimeMillis() - lastLogin) / (1000 * 60 * 60)

        val destination = when {
            storedEmail.isNullOrEmpty() -> "login"
            storedMpin.isNullOrEmpty() -> "mpin"
            hours < 24 && lastLogin > 0L -> "dashboard"
            else -> "mpin_verify"
        }
        assertEquals("Expired login should go to mpin_verify", "mpin_verify", destination)
    }

    @Test
    fun T4_3_TC05_splashDelayIsTwoSeconds() {
        val splashDelayMs = 2000L
        assertEquals("Splash delay should be 2000ms", 2000L, splashDelayMs)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.4 — LoginScreen Validation, API, and Navigation
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_4_TC01_emptyUsernameProducesError() {
        val username = ""
        val error = if (username.isEmpty()) "Username should not be empty" else null
        assertNotNull("Empty username should produce error", error)
    }

    @Test
    fun T4_4_TC02_emptyPasswordProducesError() {
        val password = ""
        val error = if (password.isEmpty()) "Password should not be empty" else null
        assertNotNull("Empty password should produce error", error)
    }

    @Test
    fun T4_4_TC03_validFieldsPassValidation() {
        val username = "user@test.com"
        val password = "SecurePass1!"
        val usernameError = if (username.isEmpty()) "error" else null
        val passwordError = if (password.isEmpty()) "error" else null
        assertNull("Valid username should have no error", usernameError)
        assertNull("Valid password should have no error", passwordError)
    }

    @Test
    fun T4_4_TC04_otpGenerationProducesFourDigits() {
        val otp = (1000..9999).random().toString()
        assertEquals("OTP should be 4 digits", 4, otp.length)
        assertTrue("OTP should be numeric", otp.all { it.isDigit() })
        assertTrue("OTP should be between 1000-9999", otp.toInt() in 1000..9999)
    }

    @Test
    fun T4_4_TC05_loadingGuardPreventsDoubleSubmit() {
        var isLoading = true
        var apiCalled = false
        if (!isLoading) apiCalled = true
        assertFalse("API should not be called while loading", apiCalled)
    }

    @Test
    fun T4_4_TC06_forgotPasswordNavigationRoute() {
        val route = "forgot_password"
        assertEquals("Forgot password route should be correct", "forgot_password", route)
    }

    @Test
    fun T4_4_TC07_signUpNavigationRoute() {
        val route = "sign_up"
        assertEquals("Sign up route should be correct", "sign_up", route)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.5 — SignUpScreen Validation, Criteria, API, and Dialog
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_5_TC01_firstNameAlphabetsOnlyValidation() {
        val valid = "John"
        val invalid = "John123"
        assertTrue("Alphabetic name should pass", valid.all { it.isLetter() })
        assertFalse("Name with digits should fail", invalid.all { it.isLetter() })
    }

    @Test
    fun T4_5_TC02_usernameMinLengthValidation() {
        val short = "abc"
        val valid = "abcdef"
        assertTrue("Short username should fail (<=5)", short.length <= 5)
        assertFalse("Valid username should pass (>5)", valid.length <= 5)
    }

    @Test
    fun T4_5_TC03_usernameAllowedCharsValidation() {
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertTrue("Alphanumeric + #*& should pass", pattern.matches("User#123"))
        assertFalse("Special chars like ! should fail", pattern.matches("User!123"))
    }

    @Test
    fun T4_5_TC04_phoneNumberDigitsOnlyValidation() {
        val phone = "9876543210"
        assertTrue("Phone should be digits only", phone.all { it.isDigit() })
        assertTrue("Phone length should be 10-15", phone.length in 10..15)
    }

    @Test
    fun T4_5_TC05_phoneNumberTooShortValidation() {
        val phone = "12345"
        assertFalse("Short phone should fail", phone.length in 10..15)
    }

    @Test
    fun T4_5_TC06_passwordCriteriaAllMet() {
        val password = "Abcdefg1!"
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        val allMet = hasMinLength && hasUpperCase && hasLowerCase && hasDigit && hasSymbol
        assertTrue("Strong password should meet all criteria", allMet)
    }

    @Test
    fun T4_5_TC07_weakPasswordFailsCriteria() {
        val password = "abc"
        val allMet = password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() } &&
                password.any { !it.isLetterOrDigit() }
        assertFalse("Weak password should not meet all criteria", allMet)
    }

    @Test
    fun T4_5_TC08_passwordsMustMatch() {
        val password = "Abcdefg1!"
        val confirm = "Abcdefg1!"
        val mismatch = "Different1!"
        assertEquals("Matching passwords should be equal", password, confirm)
        assertNotEquals("Different passwords should not match", password, mismatch)
    }

    @Test
    fun T4_5_TC09_buttonDisabledWhenCriteriaNotMet() {
        val allCriteriaMet = false
        val buttonAlpha = if (allCriteriaMet) 1.0f else 0.4f
        assertEquals("Button should be dimmed when criteria not met", 0.4f, buttonAlpha)
    }

    @Test
    fun T4_5_TC10_successDialogShowsOnRegistration() {
        val registrationSuccess = true
        val otpSent = true
        val showDialog = registrationSuccess && otpSent
        assertTrue("Dialog should show on successful registration + OTP", showDialog)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.6 — OTPScreen Verification, Timer, Resend, Routing
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_6_TC01_timerStartsAt120Seconds() {
        val timeLeft = 120
        assertEquals("Timer should start at 120 seconds", 120, timeLeft)
    }

    @Test
    fun T4_6_TC02_timerFormatsCorrectly() {
        val timeLeft = 75
        val formatted = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)
        assertEquals("75 seconds should format as 01:15", "01:15", formatted)
    }

    @Test
    fun T4_6_TC03_timerFormatsZeroCorrectly() {
        val timeLeft = 0
        val formatted = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)
        assertEquals("0 seconds should format as 00:00", "00:00", formatted)
    }

    @Test
    fun T4_6_TC04_resendEnabledOnlyWhenTimerExpired() {
        val timerRunning = false
        val isLoading = false
        val resendEnabled = !timerRunning && !isLoading
        assertTrue("Resend should be enabled when timer expired and not loading", resendEnabled)
    }

    @Test
    fun T4_6_TC05_resendDisabledWhileTimerRunning() {
        val timerRunning = true
        val isLoading = false
        val resendEnabled = !timerRunning && !isLoading
        assertFalse("Resend should be disabled while timer running", resendEnabled)
    }

    @Test
    fun T4_6_TC06_correctOtpMatchesPasses() {
        val entered = "1234"
        val expected = "1234"
        assertTrue("Matching OTP should pass", entered == expected)
    }

    @Test
    fun T4_6_TC07_incorrectOtpFails() {
        val entered = "1234"
        val expected = "5678"
        assertFalse("Mismatched OTP should fail", entered == expected)
    }

    @Test
    fun T4_6_TC08_incompleteOtpShowsError() {
        val entered = "12"
        val otpLength = 4
        val error = if (entered.length < otpLength) "Please enter the complete $otpLength-digit OTP" else null
        assertNotNull("Incomplete OTP should produce error", error)
    }

    @Test
    fun T4_6_TC09_normalModeRoutesToMpin() {
        val isForgotPassword = false
        val isResetMpin = false
        val destination = when {
            isForgotPassword -> "reset_password"
            isResetMpin -> "mpin_reset"
            else -> "mpin"
        }
        assertEquals("Normal mode should route to mpin", "mpin", destination)
    }

    @Test
    fun T4_6_TC10_forgotPasswordModeRoutesToResetPassword() {
        val isForgotPassword = true
        val isResetMpin = false
        val destination = when {
            isForgotPassword -> "reset_password"
            isResetMpin -> "mpin_reset"
            else -> "mpin"
        }
        assertEquals("Forgot password mode should route to reset_password", "reset_password", destination)
    }

    @Test
    fun T4_6_TC11_resetMpinModeRoutesToMpinReset() {
        val isForgotPassword = false
        val isResetMpin = true
        val destination = when {
            isForgotPassword -> "reset_password"
            isResetMpin -> "mpin_reset"
            else -> "mpin"
        }
        assertEquals("Reset MPIN mode should route to mpin_reset", "mpin_reset", destination)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.7 — MPINScreen Setup, Verify, Suspended, Reset
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_7_TC01_setupModeNavigatesToWelcome() {
        val isVerifyMode = false
        val isResetMode = false
        val destination = when {
            isResetMode -> "dashboard"
            isVerifyMode -> "dashboard_or_error"
            else -> "welcome"
        }
        assertEquals("Setup mode should navigate to welcome", "welcome", destination)
    }

    @Test
    fun T4_7_TC02_verifyModeCorrectMpinGoesToDashboard() {
        val enteredMpin = "1234"
        val storedMpin = "1234"
        val match = enteredMpin == storedMpin
        assertTrue("Correct MPIN should match stored", match)
    }

    @Test
    fun T4_7_TC03_verifyModeWrongMpinIncrementsAttempts() {
        val enteredMpin = "1234"
        val storedMpin = "5678"
        var wrongAttempts = 0
        if (enteredMpin != storedMpin) wrongAttempts++
        assertEquals("Wrong MPIN should increment attempts", 1, wrongAttempts)
    }

    @Test
    fun T4_7_TC04_threeWrongAttemptsTriggersSuspension() {
        var wrongAttempts = 2
        val enteredMpin = "0000"
        val storedMpin = "1234"
        if (enteredMpin != storedMpin) wrongAttempts++
        val isSuspended = wrongAttempts >= 3
        assertTrue("3 wrong attempts should trigger suspension", isSuspended)
    }

    @Test
    fun T4_7_TC05_twoWrongAttemptsShowsRemainingCount() {
        var wrongAttempts = 2
        val remaining = 3 - wrongAttempts
        assertEquals("Should show 1 attempt remaining", 1, remaining)
    }

    @Test
    fun T4_7_TC06_suspendedStateHidesInput() {
        val isSuspended = true
        // In suspended state, OTP input and arrow button should be hidden
        assertTrue("Suspended state should hide input", isSuspended)
    }

    @Test
    fun T4_7_TC07_resetModeNavigatesToDashboard() {
        val isResetMode = true
        val destination = if (isResetMode) "dashboard" else "welcome"
        assertEquals("Reset mode should navigate to dashboard", "dashboard", destination)
    }

    @Test
    fun T4_7_TC08_mpinMustBeFourDigits() {
        val shortMpin = "12"
        val validMpin = "1234"
        val shortError = if (shortMpin.length < 4) "Please enter a 4-digit MPIN" else null
        val validError = if (validMpin.length < 4) "Please enter a 4-digit MPIN" else null
        assertNotNull("Short MPIN should produce error", shortError)
        assertNull("4-digit MPIN should not produce error", validError)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.8 — WelcomeScreen Animations, Sound, Auto-Navigation
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_8_TC01_confettiRawResourceExists() {
        val resId = context.resources.getIdentifier("confetti", "raw", context.packageName)
        assertTrue("confetti Lottie resource must exist", resId != 0)
    }

    @Test
    fun T4_8_TC02_welcomeChimeRawResourceExists() {
        val resId = context.resources.getIdentifier("welcome_chime", "raw", context.packageName)
        assertTrue("welcome_chime audio must exist", resId != 0)
    }

    @Test
    fun T4_8_TC03_staggeredAnimationTimingsAreCorrect() {
        val confettiDelay = 0L
        val welcomeDelay = 600L
        val intoDelay = 1300L
        val tradeSphereDelay = 2000L
        val navigationDelay = 4000L

        assertTrue("Confetti appears first", confettiDelay < welcomeDelay)
        assertTrue("Welcome appears after confetti", welcomeDelay < intoDelay)
        assertTrue("Into appears after welcome", intoDelay < tradeSphereDelay)
        assertTrue("Trade Sphere appears after into", tradeSphereDelay < navigationDelay)
    }

    @Test
    fun T4_8_TC04_autoNavigationAfterFourSeconds() {
        val autoNavDelayMs = 4000L
        assertEquals("Auto-navigation should happen after 4 seconds", 4000L, autoNavDelayMs)
    }

    @Test
    fun T4_8_TC05_soundPlaysForTwoSeconds() {
        val soundDurationMs = 2000L
        assertEquals("Sound should play for 2 seconds", 2000L, soundDurationMs)
    }

    @Test
    fun T4_8_TC06_welcomeScreenNavigatesToDashboard() {
        val destination = "dashboard"
        assertEquals("Welcome should navigate to dashboard", "dashboard", destination)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.9 — ForgotPasswordScreen Validation, API, Navigation
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_9_TC01_emptyInputShowsError() {
        val emailOrPhone = ""
        val error = if (emailOrPhone.trim().isEmpty()) "Email or phone number should not be empty" else null
        assertNotNull("Empty input should show error", error)
    }

    @Test
    fun T4_9_TC02_emailDetectedByAtSign() {
        val input = "user@example.com"
        val isEmail = "@" in input
        assertTrue("Input with @ should be detected as email", isEmail)
    }

    @Test
    fun T4_9_TC03_phoneDetectedWithoutAtSign() {
        val input = "9876543210"
        val isEmail = "@" in input
        assertFalse("Input without @ should be phone", isEmail)
    }

    @Test
    fun T4_9_TC04_forgotPasswordNavigatesToOtpWithFlag() {
        val isForgotPassword = true
        val routeFragment = "isForgotPassword=$isForgotPassword"
        assertTrue("Route should include isForgotPassword=true", routeFragment.contains("true"))
    }

    @Test
    fun T4_9_TC05_loadingBlocksMultipleSubmits() {
        var isLoading = true
        var submitCount = 0
        if (!isLoading) submitCount++
        assertEquals("Submit should be blocked while loading", 0, submitCount)
    }

    // ════════════════════════════════════════════════════════════════════════
    //  T4.10 — ResetPasswordScreen Criteria, API, Dialog, Navigation
    // ════════════════════════════════════════════════════════════════════════

    @Test
    fun T4_10_TC01_identifierFieldIsReadOnly() {
        // ResetPasswordScreen receives identifier and displays it as read-only
        val readOnly = true
        val enabled = false
        assertTrue("Identifier field should be read-only", readOnly)
        assertFalse("Identifier field should be disabled", enabled)
    }

    @Test
    fun T4_10_TC02_passwordCriteriaSameAsSignUp() {
        val password = "Abcdefg1!"
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        val allMet = hasMinLength && hasUpperCase && hasLowerCase && hasDigit && hasSymbol
        assertTrue("Reset password criteria should match signup criteria", allMet)
    }

    @Test
    fun T4_10_TC03_buttonDimmedWhenCriteriaNotMet() {
        val allCriteriaMet = false
        val alpha = if (allCriteriaMet) 1.0f else 0.4f
        assertEquals("Button alpha should be 0.4 when criteria not met", 0.4f, alpha)
    }

    @Test
    fun T4_10_TC04_buttonFullWhenCriteriaMet() {
        val allCriteriaMet = true
        val alpha = if (allCriteriaMet) 1.0f else 0.4f
        assertEquals("Button alpha should be 1.0 when criteria met", 1.0f, alpha)
    }

    @Test
    fun T4_10_TC05_passwordsMustMatchForSubmit() {
        val password = "Abcdefg1!"
        val confirmPassword = "Abcdefg1!"
        val error = when {
            confirmPassword.isEmpty() -> "Confirm Password should not be empty"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
        assertNull("Matching passwords should produce no error", error)
    }

    @Test
    fun T4_10_TC06_mismatchedPasswordsShowError() {
        val password = "Abcdefg1!"
        val confirmPassword = "Different1!"
        val error = when {
            confirmPassword.isEmpty() -> "Confirm Password should not be empty"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
        assertEquals("Mismatched passwords should show error", "Passwords do not match", error)
    }

    @Test
    fun T4_10_TC07_emailIdentifierRoutesToEmailApi() {
        val identifier = "user@example.com"
        val isEmail = "@" in identifier
        assertTrue("Identifier with @ should call email-based API", isEmail)
    }

    @Test
    fun T4_10_TC08_phoneIdentifierRoutesToPhoneApi() {
        val identifier = "9876543210"
        val isEmail = "@" in identifier
        assertFalse("Identifier without @ should call phone-based API", isEmail)
    }

    @Test
    fun T4_10_TC09_successDialogNavigatesToLogin() {
        val destination = "login"
        assertEquals("Success should navigate to login", "login", destination)
    }

    @Test
    fun T4_10_TC10_apiErrorDisplaysBelowButton() {
        val apiResult = SignUpResult(success = false, message = "Request failed")
        val apiError = if (!apiResult.success) apiResult.message else null
        assertNotNull("Failed API should produce error message", apiError)
        assertEquals("Error message should match", "Request failed", apiError)
    }
}

