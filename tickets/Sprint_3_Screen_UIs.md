# Sprint 3 - Screen UI Layouts and Shared UI Helpers (10 Tickets)

Goal: Build the visual layout of every screen (static shells, no business logic) plus shared UI helpers.
Estimated Time: 180 min | Dependencies FROM: Sprint 1 (theme), Sprint 2 (components) | Inter-task Dependencies: NONE

## T3.1 - SplashScreen Static UI Layout
- File: app/src/main/java/.../SplashScreen.kt
- Scope: Accept NavController parameter. Full-screen Box with screen_background. Centered Column: Image (ic_stock_logo, 300dp) and Text (app name, white, Poppins Bold, 24sp). No LaunchedEffect, no Paper DB, no navigation.
- Expected Output: Centered logo above app name on dark blue background. Static.
- Time: 15 min

## T3.2 - LoginScreen Static UI Layout
- File: app/src/main/java/.../LoginScreen.kt
- Scope: Accept NavController. Box with screen_background and 24dp padding. Column with: Logo (top 100dp), Login title, 60dp spacer, CustomTextField for username, 20dp spacer, CustomTextField for password (isPassword), 10dp spacer, Forgot Password text (right-aligned), 40dp spacer, 86dp circular Box (CircleShape, button_background_color, white border) with ArrowForward icon, bottom text. States: username, password.
- Expected Output: Complete login form renders. Fields accept input. No actions.
- Time: 25 min

## T3.3 - SignUpScreen Static UI Layout
- File: app/src/main/java/.../SignUpScreen.kt
- Scope: Accept NavController. Scrollable Column. Logo, Sign Up title, 7 CustomTextFields (First Name, Last Name, Username, Email, Phone Number, Password, Confirm Password) with 16dp spacing, circular arrow button, bottom login link. States for all 7 fields.
- Expected Output: Scrollable form with 7 fields. All accept input.
- Time: 25 min

## T3.4 - OTPScreen Static UI Layout
- File: app/src/main/java/.../OTPScreen.kt
- Scope: Accept NavController, email, expectedOtp, isResetMpin, isForgotPassword. Derive otpLength (6 if forgotPassword, else 4). Logo, OTP title, OTPInput, Row with timer text and Resend Now, info text, circular arrow button. States: enteredOtp, timeLeft=120.
- Expected Output: OTP boxes render (4 or 6 based on mode). Static timer text.
- Time: 20 min

## T3.5 - MPINScreen Static UI Layout
- File: app/src/main/java/.../MPINScreen.kt
- Scope: Accept NavController, isVerifyMode, isResetMode. Logo, mode-aware title (Set New MPIN / Enter MPIN / MPIN), subtitle if verifyMode, OTPInput (4 digits), circular arrow button, biometric icon at bottom. State: enteredMpin.
- Expected Output: Correct title per mode. 4-digit input. Arrow button. Biometric icon.
- Time: 20 min

## T3.6 - WelcomeScreen Static UI Layout
- File: app/src/main/java/.../WelcomeScreen.kt
- Scope: Accept NavController. Full-screen Box with screen_background. Column with Welcome (white, Bold, 40sp), into (text_color, Normal, 20sp), Trade Sphere (white, Bold, 36sp). All centered. No animations, no Lottie, no sound, no auto-navigation.
- Expected Output: Three text lines centered on dark background. Static screen.
- Time: 10 min

## T3.7 - ForgotPasswordScreen Static UI Layout
- File: app/src/main/java/.../ForgotPasswordScreen.kt
- Scope: Accept NavController. Logo, Forgot Password title, CustomTextField for email/phone, circular arrow button. State: emailOrPhone.
- Expected Output: Input field and submit button render. Field accepts input.
- Time: 15 min

## T3.8 - ResetPasswordScreen Static UI Layout
- File: app/src/main/java/.../ResetPasswordScreen.kt
- Scope: Accept NavController, identifier. Scrollable Column. Logo, Reset Password title, CustomTextField (identifier, readOnly, disabled), CustomTextField for password (isPassword), CustomTextField for confirm password (isPassword), circular arrow button. States: password, confirmPassword.
- Expected Output: Frozen identifier field. Two password fields. Submit button.
- Time: 20 min

## T3.9 - PasswordCriteriaRow Shared Composable
- File: app/src/main/java/.../reusables/PasswordCriteriaRow.kt
- Scope: Composable function accepting label (String) and met (Boolean). Row with: Check icon (green) if met, Close icon (red) if not, 6dp spacer, Text with label in matching color (green/red, 12sp, PoppinsFamily). Icon size 16dp. Vertical padding 2dp.
- Expected Output: Renders green check + green text when criteria met. Red cross + red text when not met. Reusable by SignUpScreen and ResetPasswordScreen.
- Time: 15 min

## T3.10 - CircularSubmitButton Shared Composable
- File: app/src/main/java/.../reusables/CircularSubmitButton.kt
- Scope: Composable accepting: onClick, isLoading (Boolean), enabled (Boolean, default true), modifier. 86dp circular Box: CircleShape, button_background_color (dimmed alpha 0.4 when disabled), 2dp white border. Content: CircularProgressIndicator (32dp, white, 3dp stroke) when isLoading, else ArrowForward icon (white, 32dp, dimmed when disabled). Clickable guarded by isLoading and enabled.
- Expected Output: Shows arrow when idle, spinner when loading. Dimmed when disabled. Reusable by all auth screens.
- Time: 15 min

## Parallel Validation: Each screen (T3.1-T3.8) is a separate file. T3.9 and T3.10 are standalone helpers not imported by any Sprint 3 screen (screens use inline implementations; helpers prepared for Sprint 4). All parallel safe.
