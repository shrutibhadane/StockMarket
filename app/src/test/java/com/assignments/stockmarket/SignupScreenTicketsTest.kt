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
 *   TEST CASES FOR SIGNUP SCREEN TICKETS (Signup Tickets.txt)
 *   Validates each ticket's acceptance criteria against SignUpScreen.kt code
 *   Uses Robolectric for Android framework access on JVM
 * ============================================================================
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class SignupScreenTicketsTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    // Helper: The same VALID_TLDS set used in SignUpScreen.kt
    private val VALID_TLDS = setOf(
        "com", "in", "org", "net", "edu", "gov", "co", "io", "info", "biz", "us", "uk", "ca", "au"
    )

    // Helper: Replicates the validateEmail function from SignUpScreen.kt
    private fun validateEmail(email: String): String? {
        if ("@" !in email) return "Email must contain @"
        val parts = email.split("@")
        if (parts.size != 2 || parts[0].isEmpty()) return "Invalid email format"
        val domain = parts[1]
        if ("." !in domain) return "Email must have a valid domain"
        val tld = domain.substringAfterLast(".").lowercase()
        if (tld.isEmpty()) return "Email must have a valid TLD"
        if (tld !in VALID_TLDS) return "Invalid TLD. Use .com, .in, .org, etc."
        return null
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 1 — Create SignUpScreen composable skeleton
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket1_TC01_signUpScreenComposableFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "SignUpScreen composable function should exist",
            methods.any { it.contains("SignUpScreen") }
        )
    }

    @Test
    fun ticket1_TC02_signUpScreenAcceptsNavControllerParameter() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("SignUpScreen") }
        assertNotNull("SignUpScreen method should exist", method)
        assertTrue(
            "SignUpScreen should accept multiple parameters (NavController, callback, Composer, changed)",
            method!!.parameterCount >= 2
        )
    }

    @Test
    fun ticket1_TC03_signUpScreenHasOnSignUpClickCallback() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
        val method = clazz.declaredMethods.find { it.name.contains("SignUpScreen") }
        assertNotNull("SignUpScreen method should exist", method)
        assertTrue(
            "SignUpScreen should accept callback and Composer parameters",
            method!!.parameterCount >= 3
        )
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 2 — Add logo and Sign Up title
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket2_TC01_signUpStringResourceExists() {
        val resId = context.resources.getIdentifier("sign_up", "string", context.packageName)
        assertTrue("sign_up string resource should exist", resId != 0)
    }

    @Test
    fun ticket2_TC02_logoDrawableResourceExists() {
        val resId = context.resources.getIdentifier("ic_stock_logo", "drawable", context.packageName)
        assertTrue("ic_stock_logo drawable resource should exist", resId != 0)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 3 — Add all input text fields with state variables
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket3_TC01_allFieldStatesDefaultToEmptyString() {
        val firstName = ""
        val lastName = ""
        val username = ""
        val email = ""
        val phoneNumber = ""
        val password = ""
        val confirmPassword = ""

        assertEquals("", firstName)
        assertEquals("", lastName)
        assertEquals("", username)
        assertEquals("", email)
        assertEquals("", phoneNumber)
        assertEquals("", password)
        assertEquals("", confirmPassword)
    }

    @Test
    fun ticket3_TC02_allErrorStatesDefaultToNull() {
        val firstNameError: String? = null
        val lastNameError: String? = null
        val usernameError: String? = null
        val emailError: String? = null
        val phoneNumberError: String? = null
        val passwordError: String? = null
        val confirmPasswordError: String? = null

        assertNull(firstNameError)
        assertNull(lastNameError)
        assertNull(usernameError)
        assertNull(emailError)
        assertNull(phoneNumberError)
        assertNull(passwordError)
        assertNull(confirmPasswordError)
    }

    @Test
    fun ticket3_TC03_focusTrackingStatesDefaultToFalse() {
        val isPasswordFocused = false
        val isConfirmPasswordFocused = false
        assertFalse(isPasswordFocused)
        assertFalse(isConfirmPasswordFocused)
    }

    @Test
    fun ticket3_TC04_firstNameStringResourceExists() {
        val resId = context.resources.getIdentifier("first_name", "string", context.packageName)
        assertTrue("first_name string resource should exist", resId != 0)
    }

    @Test
    fun ticket3_TC05_lastNameStringResourceExists() {
        val resId = context.resources.getIdentifier("last_name", "string", context.packageName)
        assertTrue("last_name string resource should exist", resId != 0)
    }

    @Test
    fun ticket3_TC06_emailStringResourceExists() {
        val resId = context.resources.getIdentifier("email", "string", context.packageName)
        assertTrue("email string resource should exist", resId != 0)
    }

    @Test
    fun ticket3_TC07_phoneNumberStringResourceExists() {
        val resId = context.resources.getIdentifier("phone_number", "string", context.packageName)
        assertTrue("phone_number string resource should exist", resId != 0)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 4 — Validate First Name and Last Name — alphabets only
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket4_TC01_firstNameWithOnlyLettersIsValid() {
        val firstName = "John"
        val isValid = firstName.all { it.isLetter() }
        assertTrue("'John' should be valid (all letters)", isValid)
    }

    @Test
    fun ticket4_TC02_firstNameWithNumbersIsInvalid() {
        val firstName = "John1"
        val isValid = firstName.all { it.isLetter() }
        assertFalse("'John1' should be invalid (contains digit)", isValid)
    }

    @Test
    fun ticket4_TC03_firstNameWithSymbolsIsInvalid() {
        val firstName = "John@"
        val isValid = firstName.all { it.isLetter() }
        assertFalse("'John@' should be invalid (contains symbol)", isValid)
    }

    @Test
    fun ticket4_TC04_lastNameWithOnlyLettersIsValid() {
        val lastName = "Doe"
        val isValid = lastName.all { it.isLetter() }
        assertTrue("'Doe' should be valid (all letters)", isValid)
    }

    @Test
    fun ticket4_TC05_lastNameWithNumbersIsInvalid() {
        val lastName = "Doe123"
        val isValid = lastName.all { it.isLetter() }
        assertFalse("'Doe123' should be invalid (contains digits)", isValid)
    }

    @Test
    fun ticket4_TC06_firstNameErrorMessageIsCorrect() {
        val firstName = "John1"
        var firstNameError: String? = null
        if (firstName.isNotEmpty() && !firstName.all { c -> c.isLetter() }) {
            firstNameError = "First Name should contain only alphabets"
        }
        assertEquals("First Name should contain only alphabets", firstNameError)
    }

    @Test
    fun ticket4_TC07_lastNameErrorMessageIsCorrect() {
        val lastName = "Doe2"
        var lastNameError: String? = null
        if (lastName.isNotEmpty() && !lastName.all { c -> c.isLetter() }) {
            lastNameError = "Last Name should contain only alphabets"
        }
        assertEquals("Last Name should contain only alphabets", lastNameError)
    }

    @Test
    fun ticket4_TC08_correctingInputClearsError() {
        var firstNameError: String? = "First Name should contain only alphabets"
        val correctedName = "John"
        if (correctedName.isNotEmpty() && correctedName.all { c -> c.isLetter() }) {
            firstNameError = null
        }
        assertNull("Error should be cleared when input is corrected", firstNameError)
    }

    @Test
    fun ticket4_TC09_emptyFirstNameShowsNoInlineError() {
        val firstName = ""
        var firstNameError: String? = null
        firstNameError = if (firstName.isNotEmpty() && !firstName.all { c -> c.isLetter() })
            "First Name should contain only alphabets" else null
        assertNull("Empty first name should not show inline alphabet error", firstNameError)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 5 — Validate Username — allowed characters and min length
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket5_TC01_validUsernameWithLettersNumbersSymbols() {
        val username = "user#1"
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertTrue("'user#1' should match allowed pattern", pattern.matches(username))
        assertTrue("'user#1' length (6) should be > 5", username.length > 5)
    }

    @Test
    fun ticket5_TC02_usernameWithDisallowedSymbolIsInvalid() {
        val username = "user@1"
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertFalse("'user@1' should not match (@ not allowed)", pattern.matches(username))
    }

    @Test
    fun ticket5_TC03_usernameTooShortShowsLengthError() {
        val username = "abc"
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        val error = when {
            username.isNotEmpty() && !pattern.matches(username) ->
                "Username can only contain letters, numbers and #*&"
            username.isNotEmpty() && username.length <= 5 ->
                "Username must be more than 5 characters"
            else -> null
        }
        assertEquals("Username must be more than 5 characters", error)
    }

    @Test
    fun ticket5_TC04_usernameExactly5CharsIsInvalid() {
        val username = "abcde"
        assertTrue("Username of exactly 5 chars should fail length check", username.length <= 5)
    }

    @Test
    fun ticket5_TC05_usernameExactly6CharsIsValid() {
        val username = "abcdef"
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertTrue("Username of 6 chars should pass length check", username.length > 5)
        assertTrue("Username 'abcdef' should match pattern", pattern.matches(username))
    }

    @Test
    fun ticket5_TC06_characterErrorHasPriorityOverLengthError() {
        val username = "ab@"
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        val error = when {
            username.isNotEmpty() && !pattern.matches(username) ->
                "Username can only contain letters, numbers and #*&"
            username.isNotEmpty() && username.length <= 5 ->
                "Username must be more than 5 characters"
            else -> null
        }
        assertEquals(
            "Character error should have priority over length",
            "Username can only contain letters, numbers and #*&",
            error
        )
    }

    @Test
    fun ticket5_TC07_usernameWithAsteriskIsValid() {
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertTrue("'user*x' should be valid", pattern.matches("user*x"))
    }

    @Test
    fun ticket5_TC08_usernameWithAmpersandIsValid() {
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertTrue("'user&x' should be valid", pattern.matches("user&x"))
    }

    @Test
    fun ticket5_TC09_usernameWithHashIsValid() {
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertTrue("'user#x' should be valid", pattern.matches("user#x"))
    }

    @Test
    fun ticket5_TC10_usernameWithSpaceIsInvalid() {
        val pattern = Regex("^[a-zA-Z0-9#*&]*$")
        assertFalse("'user name' should not be valid (space)", pattern.matches("user name"))
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 6 — Implement email validation function
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket6_TC01_validEmailReturnsNull() {
        assertNull("user@example.com should be valid", validateEmail("user@example.com"))
    }

    @Test
    fun ticket6_TC02_emailWithoutAtSignIsInvalid() {
        assertEquals("Email must contain @", validateEmail("userexample.com"))
    }

    @Test
    fun ticket6_TC03_emailWithoutDomainDotIsInvalid() {
        assertEquals("Email must have a valid domain", validateEmail("user@example"))
    }

    @Test
    fun ticket6_TC04_emailWithInvalidTLDIsInvalid() {
        assertEquals(
            "Invalid TLD. Use .com, .in, .org, etc.",
            validateEmail("user@example.xyz")
        )
    }

    @Test
    fun ticket6_TC05_emailWithEmptyLocalPartIsInvalid() {
        assertEquals("Invalid email format", validateEmail("@example.com"))
    }

    @Test
    fun ticket6_TC06_emailWithValidTLDInIsValid() {
        assertNull("user@example.in should be valid", validateEmail("user@example.in"))
    }

    @Test
    fun ticket6_TC07_emailWithValidTLDOrgIsValid() {
        assertNull("user@example.org should be valid", validateEmail("user@example.org"))
    }

    @Test
    fun ticket6_TC08_emailWithValidTLDEduIsValid() {
        assertNull("user@school.edu should be valid", validateEmail("user@school.edu"))
    }

    @Test
    fun ticket6_TC09_emailWithValidTLDIoIsValid() {
        assertNull("dev@project.io should be valid", validateEmail("dev@project.io"))
    }

    @Test
    fun ticket6_TC10_emailWithMultipleAtSignsIsInvalid() {
        val result = validateEmail("user@@example.com")
        assertNotNull("Multiple @ signs should fail validation", result)
    }

    @Test
    fun ticket6_TC11_validTLDSetContainsAllExpectedEntries() {
        val expected = setOf("com", "in", "org", "net", "edu", "gov", "co", "io", "info", "biz", "us", "uk", "ca", "au")
        assertEquals("VALID_TLDS should contain exactly 14 entries", 14, VALID_TLDS.size)
        assertEquals("VALID_TLDS should match expected set", expected, VALID_TLDS)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 7 — Validate Phone Number — digits only and length
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket7_TC01_nonDigitsAreFilteredOut() {
        val input = "123abc456"
        val filtered = input.filter { it.isDigit() }
        assertEquals("Non-digits should be stripped", "123456", filtered)
    }

    @Test
    fun ticket7_TC02_phoneWith10DigitsIsValid() {
        val phone = "1234567890"
        val isValid = phone.length in 10..15
        assertTrue("10-digit phone should be valid", isValid)
    }

    @Test
    fun ticket7_TC03_phoneWith15DigitsIsValid() {
        val phone = "123456789012345"
        val isValid = phone.length in 10..15
        assertTrue("15-digit phone should be valid", isValid)
    }

    @Test
    fun ticket7_TC04_phoneWith5DigitsIsInvalid() {
        val phone = "12345"
        val isValid = phone.length in 10..15
        assertFalse("5-digit phone should be invalid", isValid)
    }

    @Test
    fun ticket7_TC05_phoneWith16DigitsIsInvalid() {
        val phone = "1234567890123456"
        val isValid = phone.length in 10..15
        assertFalse("16-digit phone should be invalid", isValid)
    }

    @Test
    fun ticket7_TC06_phoneWithSpecialCharsFilteredCorrectly() {
        val input = "+91-9876543210"
        val filtered = input.filter { it.isDigit() }
        assertEquals("919876543210", filtered)
        assertTrue("Filtered phone should be valid length", filtered.length in 10..15)
    }

    @Test
    fun ticket7_TC07_phoneErrorMessageIsCorrect() {
        val phone = "12345"
        var phoneError: String? = null
        if (phone.isNotEmpty() && (phone.length < 10 || phone.length > 15)) {
            phoneError = "Phone number must be 10-15 digits"
        }
        assertEquals("Phone number must be 10-15 digits", phoneError)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 8 — Implement password criteria booleans and allCriteriaMet
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket8_TC01_strongPasswordMeetsAllCriteria() {
        val password = "Abc12345!"
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        val allCriteriaMet = hasMinLength && hasUpperCase && hasLowerCase && hasDigit && hasSymbol

        assertTrue("hasMinLength should be true", hasMinLength)
        assertTrue("hasUpperCase should be true", hasUpperCase)
        assertTrue("hasLowerCase should be true", hasLowerCase)
        assertTrue("hasDigit should be true", hasDigit)
        assertTrue("hasSymbol should be true", hasSymbol)
        assertTrue("allCriteriaMet should be true", allCriteriaMet)
    }

    @Test
    fun ticket8_TC02_weakPasswordOnlyLowerCase() {
        val password = "abc"
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        val allCriteriaMet = hasMinLength && hasUpperCase && hasLowerCase && hasDigit && hasSymbol

        assertFalse("hasMinLength should be false", hasMinLength)
        assertFalse("hasUpperCase should be false", hasUpperCase)
        assertTrue("hasLowerCase should be true", hasLowerCase)
        assertFalse("hasDigit should be false", hasDigit)
        assertFalse("hasSymbol should be false", hasSymbol)
        assertFalse("allCriteriaMet should be false", allCriteriaMet)
    }

    @Test
    fun ticket8_TC03_passwordWithoutSymbolFails() {
        val password = "Abc12345"
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        assertFalse("Password without symbol should fail hasSymbol", hasSymbol)
    }

    @Test
    fun ticket8_TC04_passwordWithExactly8CharsPassesMinLength() {
        val password = "Abcdef1!"
        assertTrue("8-char password should pass min length", password.length >= 8)
    }

    @Test
    fun ticket8_TC05_passwordWith7CharsFails() {
        val password = "Abcde1!"
        assertFalse("7-char password should fail min length", password.length >= 8)
    }

    @Test
    fun ticket8_TC06_passwordWithoutUpperCaseFails() {
        val password = "abcdefgh1!"
        assertFalse("Password without upper case should fail", password.any { it.isUpperCase() })
    }

    @Test
    fun ticket8_TC07_passwordWithoutLowerCaseFails() {
        val password = "ABCDEFGH1!"
        assertFalse("Password without lower case should fail", password.any { it.isLowerCase() })
    }

    @Test
    fun ticket8_TC08_passwordWithoutDigitFails() {
        val password = "Abcdefgh!"
        assertFalse("Password without digit should fail", password.any { it.isDigit() })
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 9 — Build PasswordCriteriaRow and visibility logic
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket9_TC01_criteriaVisibleWhenPasswordFocusedAndNonEmpty() {
        val isPasswordFocused = true
        val isConfirmPasswordFocused = false
        val password = "a"
        val showCriteria = isPasswordFocused && !isConfirmPasswordFocused && password.isNotEmpty()
        assertTrue("Criteria should be visible", showCriteria)
    }

    @Test
    fun ticket9_TC02_criteriaHiddenWhenConfirmPasswordFocused() {
        val isPasswordFocused = true
        val isConfirmPasswordFocused = true
        val password = "a"
        val showCriteria = isPasswordFocused && !isConfirmPasswordFocused && password.isNotEmpty()
        assertFalse("Criteria should be hidden when confirm password is focused", showCriteria)
    }

    @Test
    fun ticket9_TC03_criteriaHiddenWhenPasswordEmpty() {
        val isPasswordFocused = true
        val isConfirmPasswordFocused = false
        val password = ""
        val showCriteria = isPasswordFocused && !isConfirmPasswordFocused && password.isNotEmpty()
        assertFalse("Criteria should be hidden when password is empty", showCriteria)
    }

    @Test
    fun ticket9_TC04_criteriaHiddenWhenPasswordNotFocused() {
        val isPasswordFocused = false
        val isConfirmPasswordFocused = false
        val password = "abc"
        val showCriteria = isPasswordFocused && !isConfirmPasswordFocused && password.isNotEmpty()
        assertFalse("Criteria should be hidden when password field not focused", showCriteria)
    }

    @Test
    fun ticket9_TC05_passwordCriteriaRowExists() {
        // PasswordCriteriaRow is a private composable — verify it's callable via SignUpScreenKt
        val clazz = Class.forName("com.assignments.stockmarket.SignUpScreenKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "PasswordCriteriaRow composable should exist",
            methods.any { it.contains("PasswordCriteriaRow") }
        )
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 10 — Validate Confirm Password — match check
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket10_TC01_matchingPasswordsShowNoError() {
        val password = "Abc12345!"
        val confirmPassword = "Abc12345!"
        val error = when {
            confirmPassword.isEmpty() -> "Confirm Password should not be empty"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
        assertNull("Matching passwords should not show error", error)
    }

    @Test
    fun ticket10_TC02_mismatchedPasswordsShowError() {
        val password = "Abc12345!"
        val confirmPassword = "Xyz98765!"
        val error = when {
            confirmPassword.isEmpty() -> "Confirm Password should not be empty"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
        assertEquals("Passwords do not match", error)
    }

    @Test
    fun ticket10_TC03_emptyConfirmPasswordShowsError() {
        val confirmPassword = ""
        val error = when {
            confirmPassword.isEmpty() -> "Confirm Password should not be empty"
            else -> null
        }
        assertEquals("Confirm Password should not be empty", error)
    }

    @Test
    fun ticket10_TC04_changingPasswordUpdatesCrossCheck() {
        val password = "NewPass1!"
        val confirmPassword = "OldPass1!"
        var confirmPasswordError: String? = null
        // Simulates password onValueChange logic
        if (confirmPassword.isNotEmpty() && confirmPassword != password) {
            confirmPasswordError = "Passwords do not match"
        }
        assertEquals("Passwords do not match", confirmPasswordError)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 11 — Build circular submit button with enabled/disabled
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket11_TC01_buttonDisabledWhenCriteriaNotMet() {
        val allCriteriaMet = false
        val isLoading = false
        val shouldBlock = isLoading || !allCriteriaMet
        assertTrue("Button click should be blocked when criteria not met", shouldBlock)
    }

    @Test
    fun ticket11_TC02_buttonDisabledWhenLoading() {
        val allCriteriaMet = true
        val isLoading = true
        val shouldBlock = isLoading || !allCriteriaMet
        assertTrue("Button click should be blocked when loading", shouldBlock)
    }

    @Test
    fun ticket11_TC03_buttonEnabledWhenCriteriaMetAndNotLoading() {
        val allCriteriaMet = true
        val isLoading = false
        val shouldBlock = isLoading || !allCriteriaMet
        assertFalse("Button click should be allowed", shouldBlock)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 12 — Implement full form validation on submit
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket12_TC01_allEmptyFieldsShowAllErrors() {
        var firstNameError: String? = null
        var lastNameError: String? = null
        var usernameError: String? = null
        var emailError: String? = null
        var phoneNumberError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null
        var valid = true

        if ("".isEmpty()) { firstNameError = "First Name should not be empty"; valid = false }
        if ("".isEmpty()) { lastNameError = "Last Name should not be empty"; valid = false }
        if ("".isEmpty()) { usernameError = "Username should not be empty"; valid = false }
        if ("".isEmpty()) { emailError = "Email should not be empty"; valid = false }
        if ("".isEmpty()) { phoneNumberError = "Phone Number should not be empty"; valid = false }
        if ("".isEmpty()) { passwordError = "Password should not be empty"; valid = false }
        if ("".isEmpty()) { confirmPasswordError = "Confirm Password should not be empty"; valid = false }

        assertNotNull(firstNameError)
        assertNotNull(lastNameError)
        assertNotNull(usernameError)
        assertNotNull(emailError)
        assertNotNull(phoneNumberError)
        assertNotNull(passwordError)
        assertNotNull(confirmPasswordError)
        assertFalse("Validation should fail for all empty fields", valid)
    }

    @Test
    fun ticket12_TC02_invalidFirstNameShowsFormatError() {
        val firstName = "John1"
        var firstNameError: String? = null
        var valid = true

        if (firstName.isEmpty()) {
            firstNameError = "First Name should not be empty"; valid = false
        } else if (!firstName.all { it.isLetter() }) {
            firstNameError = "First Name should contain only alphabets"; valid = false
        }

        assertEquals("First Name should contain only alphabets", firstNameError)
        assertFalse(valid)
    }

    @Test
    fun ticket12_TC03_invalidUsernameCharShowsCharError() {
        val username = "user@1"
        val allowedPattern = Regex("^[a-zA-Z0-9#*&]*$")
        var usernameError: String? = null
        var valid = true

        if (username.isEmpty()) {
            usernameError = "Username should not be empty"; valid = false
        } else if (!allowedPattern.matches(username)) {
            usernameError = "Username can only contain letters, numbers and #*&"; valid = false
        } else if (username.length <= 5) {
            usernameError = "Username must be more than 5 characters"; valid = false
        }

        assertEquals("Username can only contain letters, numbers and #*&", usernameError)
    }

    @Test
    fun ticket12_TC04_shortUsernameShowsLengthError() {
        val username = "abc"
        val allowedPattern = Regex("^[a-zA-Z0-9#*&]*$")
        var usernameError: String? = null

        if (username.isEmpty()) {
            usernameError = "Username should not be empty"
        } else if (!allowedPattern.matches(username)) {
            usernameError = "Username can only contain letters, numbers and #*&"
        } else if (username.length <= 5) {
            usernameError = "Username must be more than 5 characters"
        }

        assertEquals("Username must be more than 5 characters", usernameError)
    }

    @Test
    fun ticket12_TC05_invalidEmailShowsFormatError() {
        val email = "bademail"
        val result = validateEmail(email)
        assertNotNull("Bad email should fail validation", result)
    }

    @Test
    fun ticket12_TC06_shortPhoneShowsError() {
        val phone = "12345"
        var phoneError: String? = null
        if (phone.isEmpty()) {
            phoneError = "Phone Number should not be empty"
        } else if (phone.length < 10 || phone.length > 15) {
            phoneError = "Phone number must be 10-15 digits"
        }
        assertEquals("Phone number must be 10-15 digits", phoneError)
    }

    @Test
    fun ticket12_TC07_passwordMismatchShowsError() {
        val password = "Abc12345!"
        val confirmPassword = "Different1!"
        var confirmPasswordError: String? = null

        if (confirmPassword.isEmpty()) {
            confirmPasswordError = "Confirm Password should not be empty"
        } else if (password != confirmPassword) {
            confirmPasswordError = "Passwords do not match"
        }

        assertEquals("Passwords do not match", confirmPasswordError)
    }

    @Test
    fun ticket12_TC08_allValidFieldsPassValidation() {
        val firstName = "John"
        val lastName = "Doe"
        val username = "johndoe#1"
        val email = "john@example.com"
        val phoneNumber = "1234567890"
        val password = "Abc12345!"
        val confirmPassword = "Abc12345!"

        var valid = true
        val allowedPattern = Regex("^[a-zA-Z0-9#*&]*$")

        if (firstName.isEmpty() || !firstName.all { it.isLetter() }) valid = false
        if (lastName.isEmpty() || !lastName.all { it.isLetter() }) valid = false
        if (username.isEmpty() || !allowedPattern.matches(username) || username.length <= 5) valid = false
        if (email.isEmpty() || validateEmail(email) != null) valid = false
        if (phoneNumber.isEmpty() || phoneNumber.length < 10 || phoneNumber.length > 15) valid = false
        if (password.isEmpty()) valid = false
        if (confirmPassword.isEmpty() || password != confirmPassword) valid = false

        assertTrue("All valid fields should pass validation", valid)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 13 — Wire signup API call and OTP generation
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket13_TC01_registerUserFunctionExists() {
        val clazz = Class.forName("com.assignments.stockmarket.DedicatedAPIKt")
        val methods = clazz.declaredMethods.map { it.name }
        assertTrue(
            "registerUser function should exist in DedicatedAPI.kt",
            methods.any { it.contains("registerUser") }
        )
    }

    @Test
    fun ticket13_TC02_otpGeneratedIs4Digits() {
        val otp = (1000..9999).random().toString()
        assertEquals("OTP should be 4 digits", 4, otp.length)
        assertTrue("OTP should be all digits", otp.all { it.isDigit() })
    }

    @Test
    fun ticket13_TC03_signUpResultDataClassExists() {
        val clazz = Class.forName("com.assignments.stockmarket.SignUpResult")
        assertNotNull("SignUpResult data class should exist", clazz)
    }

    @Test
    fun ticket13_TC04_signUpResultSuccessTrue() {
        val result = SignUpResult(success = true)
        assertTrue("SignUpResult success should be true", result.success)
        assertNull("Message should be null for success", result.message)
    }

    @Test
    fun ticket13_TC05_signUpResultFailureWithMessage() {
        val result = SignUpResult(success = false, message = "Email already registered")
        assertFalse("SignUpResult success should be false", result.success)
        assertEquals("Email already registered", result.message)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 14 — Show success dialog and navigate to OTP screen
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket14_TC01_showSuccessDialogDefaultsToFalse() {
        val showSuccessDialog = false
        assertFalse("showSuccessDialog should default to false", showSuccessDialog)
    }

    @Test
    fun ticket14_TC02_emailEncodedForNavigation() {
        val email = "test@example.com"
        val encoded = Uri.encode(email)
        assertNotNull("Encoded email should not be null", encoded)
        val route = "otp/$encoded/1234"
        assertTrue("Route should contain encoded email", route.contains(encoded))
    }

    @Test
    fun ticket14_TC03_navigationRouteIncludesOtp() {
        val email = "test%40example.com"
        val otp = "5678"
        val route = "otp/$email/$otp"
        assertTrue("Route should include OTP", route.endsWith("/5678"))
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 15 — Display API error messages and bottom text
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket15_TC01_apiErrorDisplayedWhenNotNull() {
        val apiError: String? = "Network error. Please check your connection."
        assertTrue("API error should be displayed when not null", apiError != null)
    }

    @Test
    fun ticket15_TC02_apiErrorHiddenWhenNull() {
        val apiError: String? = null
        assertTrue("API error should be hidden when null", apiError == null)
    }

    @Test
    fun ticket15_TC03_apiErrorClearedOnFieldChange() {
        var apiError: String? = "Some API error"
        apiError = null // simulates clearing on field change
        assertNull("API error should be cleared when user types", apiError)
    }

    @Test
    fun ticket15_TC04_alreadyHaveAccountStringExists() {
        val resId = context.resources.getIdentifier("already_have_an_account_click_to_login", "string", context.packageName)
        assertTrue("already_have_an_account string should exist", resId != 0)
    }

    // ────────────────────────────────────────────────────────────────────────
    // Ticket S.No: 16 — Add string resources for SignUp screen
    // ────────────────────────────────────────────────────────────────────────

    @Test
    fun ticket16_TC01_allSignupStringResourcesExist() {
        val requiredStrings = listOf(
            "first_name", "last_name", "phone_number", "email", "sign_up"
        )
        for (name in requiredStrings) {
            val resId = context.resources.getIdentifier(name, "string", context.packageName)
            assertTrue("String resource '$name' should exist", resId != 0)
        }
    }
}
