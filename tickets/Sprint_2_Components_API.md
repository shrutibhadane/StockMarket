# Sprint 2 - Reusable Components and API Functions (10 Tickets)

Goal: Build shared UI building blocks and all network API functions.
Estimated Time: 220 min | Dependencies FROM: Sprint 1 | Inter-task Dependencies: NONE

## T2.1 - CustomTextField Reusable Composable
- File: app/src/main/java/.../reusables/CustomTextField.kt
- Scope: Parameters: placeholder, value, onValueChange, isPassword (default false), errorMessage (nullable), enabled, readOnly. Features: OutlinedTextField with RoundedCornerShape(10dp), 3dp white border, text_field_color background, white text/cursor, password visibility toggle (Visibility/VisibilityOff icons), error text below field (white, 12sp), singleLine.
- Expected Output: Renders correctly in all states: normal, password hidden/visible, error, disabled, read-only.
- Time: 30 min

## T2.2 - OTPInput Reusable Composable
- File: app/src/main/java/.../reusables/OTPInput.kt
- Scope: Parameters: otpLength (default 4), onOtpComplete, onValueChange. Row of individual digit OutlinedTextField boxes. Auto-focus advance on digit entry. Adaptive sizing: 70dp for 4 or fewer digits, 50dp for more. Number keyboard. White border on dark background. RoundedCornerShape(16dp).
- Expected Output: 4-box and 6-box variants render. Typing auto-advances focus. onOtpComplete fires on completion.
- Time: 30 min

## T2.3 - authenticateUser() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun authenticateUser(username, password): Boolean. POST to LOGIN_API_URL. Payload: user_id, password. Return true if JSON status is OK. 15s timeouts. withContext(Dispatchers.IO). Catch exceptions gracefully.
- Expected Output: Valid credentials return true. Invalid return false. Network errors return false.
- Time: 25 min

## T2.4 - registerUser() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun registerUser(firstName, lastName, username, email, phoneNumber, password): SignUpResult. POST to SIGNUP_API_URL. Payload: first_name, last_name, username, email, phone_number, password. Parse status and message from JSON.
- Expected Output: New user returns success. Duplicate email returns failure with message.
- Time: 25 min

## T2.5 - sendOtpApi() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun sendOtpApi(email, otp): Boolean. POST to SEND_OTP_API_URL. Payload: email, otp. Return true if responseCode in 200..299.
- Expected Output: Returns true when server queues OTP. False on error.
- Time: 15 min

## T2.6 - updateStatusApi() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun updateStatusApi(email): Pair of Boolean and String. POST to UPDATE_STATUS_API_URL. Payload: email. Return success flag and HTTP response body for debugging.
- Expected Output: Valid email returns Pair(true, response). Error returns Pair(false, error).
- Time: 20 min

## T2.7 - checkTheStatusApi() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun checkTheStatusApi(email): StatusResult. POST to CHECK_STATUS_API_URL. Parse email_verified and phone_verified booleans. Return default StatusResult on error.
- Expected Output: Verified user returns StatusResult(true, true). Network error returns defaults.
- Time: 20 min

## T2.8 - forgotPasswordApi() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun forgotPasswordApi(email?, phoneNumber?): ForgotPasswordResult. POST to FORGOT_PASSWORD_API_URL. Include email or phone_number based on non-null. Parse status, otp, message.
- Expected Output: Registered email returns success with 6-digit OTP. Unknown email returns failure.
- Time: 20 min

## T2.9 - resetPasswordApi() API Function
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: suspend fun resetPasswordApi(email?, phoneNumber?, password): SignUpResult. POST to RESET_PASSWORD_API_URL. Include email or phone_number plus password. Parse status and message.
- Expected Output: Valid request returns success. User can log in with new password.
- Time: 20 min

## T2.10 - validateEmail() Utility Function
- File: app/src/main/java/.../utils/ValidationUtils.kt
- Scope: fun validateEmail(email): String? (null means valid, non-null is error message). Check: contains @, valid domain, TLD from approved set (com, in, org, net, edu, gov, co, io, info, biz, us, uk, ca, au). Return specific error messages for each failure case.
- Expected Output: Valid emails return null. Invalid emails return descriptive error strings.
- Time: 15 min

## Parallel Validation: T2.1-T2.2 are separate composable files. T2.3-T2.9 are independent functions in same file (no function calls another). T2.10 is standalone utility. All parallel safe.
