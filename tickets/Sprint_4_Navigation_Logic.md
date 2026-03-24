# Sprint 4 - Navigation Graph and Business Logic (10 Tickets)

Goal: Wire all screens via navigation graph and add all business logic, validation, API integration, animations, and state management.
Estimated Time: 325 min | Dependencies FROM: Sprint 2 (APIs), Sprint 3 (screen UIs, helpers) | Inter-task Dependencies: NONE

## T4.1 - Navigation Graph and MainActivity Wiring
- File: app/src/main/java/.../navigation/AppNavigation.kt and MainActivity.kt
- Scope: Create AppNavigation composable with rememberNavController and NavHost (startDestination=splash). Define routes: splash, login, sign_up, mpin, mpin_verify (isVerifyMode=true), mpin_reset (isResetMode=true), welcome, forgot_password, otp/email/otp with isResetMpin and isForgotPassword optional bool args, reset_password/identifier. In MainActivity update setContent to call AppNavigation().
- Expected Output: App launches at splash. All routes navigable. Arguments extracted correctly.
- Time: 30 min

## T4.2 - SessionManager Paper DB Wrapper
- File: app/src/main/java/.../utils/SessionManager.kt
- Scope: Object SessionManager with functions: saveEmail(email), getEmail(): String?, saveMpin(mpin), getMpin(): String?, saveLastLoginTime(), getLastLoginTime(): Long, isSessionValid(): Boolean (checks if last login within 24 hours), clearAll(). All Paper.book() read/write operations wrapped with Dispatchers.IO. Standalone utility not required by other Sprint 4 tickets.
- Expected Output: All functions compile. Unit testable. Paper DB operations are encapsulated.
- Time: 20 min

## T4.3 - SplashScreen Session Logic
- File: app/src/main/java/.../SplashScreen.kt
- Scope: Add LaunchedEffect(Unit): 2000ms delay, read user_email from Paper DB on Dispatchers.IO. If null/empty navigate to login. If email exists, read user_mpin. If no mpin navigate to mpin. If mpin exists, read last_login_time, compute hours elapsed. If less than 24h navigate to dashboard. If 24h or more navigate to mpin_verify. All navigations use popUpTo splash inclusive.
- Expected Output: Fresh install goes to Login. Email stored no MPIN goes to MPIN setup. MPIN exists and less than 24h goes to Dashboard. 24h or more goes to MPIN verify.
- Time: 25 min

## T4.4 - LoginScreen Validation, API, and Navigation
- File: app/src/main/java/.../LoginScreen.kt
- Scope: Add states: usernameError, passwordError, authError, isLoading, coroutineScope. On submit: validate non-empty fields, call authenticateUser in coroutine. On success: generate 4-digit OTP, call sendOtpApi. If OTP sent navigate to otp/email/otp with popUpTo login inclusive. If failed set authError. On auth failure set authError. Show CircularProgressIndicator while loading. Wire Forgot Password to forgot_password route. Wire bottom text to sign_up route.
- Expected Output: Empty fields show errors. Valid credentials lead to OTP screen. Invalid show error. Links navigate correctly.
- Time: 35 min

## T4.5 - SignUpScreen Validation, Criteria, API, and Dialog
- File: app/src/main/java/.../SignUpScreen.kt
- Scope: Real-time validations in onValueChange: names alphabets only, username regex and length, email via validateEmail(), phone digits 10-15, password criteria (8+ chars, upper, lower, digit, symbol), confirm match. Show PasswordCriteriaRow items when password focused. Button dimmed when criteria not met. On submit: call registerUser, on success generate OTP, call sendOtpApi, show Congratulations AlertDialog, on OK navigate to otp/email/otp with popUpTo sign_up inclusive. Wire bottom text to login.
- Expected Output: Per-field errors. Criteria indicators update real-time. Success dialog then OTP. API errors display.
- Time: 45 min

## T4.6 - OTPScreen Verification, Timer, Resend, and Routing
- File: app/src/main/java/.../OTPScreen.kt
- Scope: Timer: LaunchedEffect counting down timeLeft from 120. Display MM:SS. Resend enabled when timer=0: if isForgotPassword call forgotPasswordApi and update expectedOtp, else generate new 4-digit OTP and sendOtpApi. Reset timer to 120 and increment resendKey. Verify: compare enteredOtp with expectedOtp. If match route by mode: isForgotPassword to reset_password, isResetMpin to mpin_reset, normal to updateStatusApi then Paper.write email then navigate to mpin. Loading spinner during API calls.
- Expected Output: Timer counts down. Resend works after expiry. Correct OTP routes to correct screen. Wrong OTP shows error.
- Time: 40 min

## T4.7 - MPINScreen Setup, Verify, Suspended, and Reset
- File: app/src/main/java/.../MPINScreen.kt
- Scope: States: wrongAttempts, isSuspended, isLoading. Setup mode: save mpin and last_login_time to Paper, navigate to welcome. Verify mode: compare with stored mpin. Correct saves login time and goes to dashboard. Wrong increments attempts. 3 or more sets isSuspended. Suspended UI: hide input, show red warning text, Reset MPIN button that reads email, generates OTP, sends via API, navigates to otp with isResetMpin=true. Reset mode: save new mpin and login time, navigate to dashboard.
- Expected Output: Setup saves and goes to welcome. Verify correct goes to dashboard. Wrong 3 times suspends. Reset MPIN sends OTP. Reset mode saves and goes to dashboard.
- Time: 40 min

## T4.8 - WelcomeScreen Animations, Sound, and Auto-Navigation
- File: app/src/main/java/.../WelcomeScreen.kt
- Scope: Lottie: rememberLottieComposition(R.raw.confetti), 3 iterations at 1.2x speed, TopStart 250dp. Staggered LaunchedEffect: 0ms showConfetti, 600ms showWelcome, 1300ms showInto, 2000ms showTradeSphere, 4000ms navigate to dashboard with popUpTo welcome inclusive. Each text in AnimatedVisibility with fadeIn + slideInVertically. Sound: DisposableEffect with MediaPlayer.create(R.raw.welcome_chime), start, stop after 2 seconds via Handler, release in onDispose.
- Expected Output: Confetti plays. Text appears sequentially. Chime for 2 seconds. Auto-navigates to dashboard.
- Time: 30 min

## T4.9 - ForgotPasswordScreen Validation, API, and Navigation
- File: app/src/main/java/.../ForgotPasswordScreen.kt
- Scope: States: inputError, apiError, isLoading. On submit: validate non-empty. Determine email vs phone. Call forgotPasswordApi. On success with OTP navigate to otp/identifier/otp with isForgotPassword=true and popUpTo forgot_password inclusive. On failure display apiError. Spinner in button while loading.
- Expected Output: Empty input shows error. Valid email navigates to OTP (6-digit mode). Unknown email shows API error.
- Time: 25 min

## T4.10 - ResetPasswordScreen Criteria, API, Dialog, and Navigation
- File: app/src/main/java/.../ResetPasswordScreen.kt
- Scope: Same 5 password criteria as SignUpScreen. Show PasswordCriteriaRow when password focused. Button dimmed when criteria not met. On submit: validate match, determine email vs phone from identifier. Call resetPasswordApi. On success show AlertDialog (Password Reset Successful), on OK navigate to login with popUpTo reset_password inclusive. On failure display apiError. Spinner while loading.
- Expected Output: Criteria update real-time. Button enabled when all met. Success dialog then login. Failure shows error.
- Time: 35 min

## Parallel Validation: T4.1 imports screen composables from Sprint 3 (previous sprint). T4.2 is standalone utility. T4.3-T4.10 each modify only their own screen file. No screen logic depends on another screen logic. Navigation calls use string routes (compile independently of AppNavigation). All parallel safe.
