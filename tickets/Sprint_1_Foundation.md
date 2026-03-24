# Sprint 1 - Project Foundation and Configuration (10 Tickets)

Goal: Set up all project config, resources, themes, API constants, and base Activity.
Estimated Time: 135 min | Inter-task Dependencies: NONE

## T1.1 - Gradle Dependency Configuration
- File: app/build.gradle.kts
- Scope: Add dependencies: Navigation Compose 2.7.7, Paper DB 2.7.2, Lottie Compose 6.4.0, Core Splash Screen 1.0.1, OkHttp 4.12.0, Coroutines Android 1.7.3, Material Icons Extended, Google Fonts, AppCompat. Set compileSdk=36, minSdk=24, jvmTarget=17, compose=true.
- Expected Output: Project syncs successfully. All libraries importable.
- Time: 20 min

## T1.2 - Color Resources
- File: app/src/main/res/values/colors.xml
- Scope: Define 20 colors: screen_background(FF001336), text_field_color(FF374560), text_color(FFD0CECE), button_background_color(FF485773), white, black, light_green_text_color(01FF41), red_text_color(FF0105), green_text_color(00FF00), light_red_text_color(FE7779), light_grey_text_color(CFCDCD), light_grey_image_color(C7C7C7), blue_text_color(04007D), light_blue_text_color(84D8FB), extra_light_blue_text_color(FFDDF3FC), light_blue_button_bg_color(BBBAFF), dark_blue_button_bg_color(1F00AB), mpin_screen_background(559BEB), nav_bar_background(FF000A1F), purple(6B0CC4).
- Expected Output: All colorResource(R.color.xxx) references resolve.
- Time: 10 min

## T1.3 - String Resources
- File: app/src/main/res/values/strings.xml
- Scope: Define all user-facing strings: app_name, login, username, password, forget_password, sign_up, confirm_password, mpin, otp, resend_now, sec_left, forgot_password, enter_your_email_phone, invalid_userid_or_password, email, first_name, last_name, phone_number, signup_failed, otp_sent_failed, otp_mismatch, otp_verification_failed, reset_password, and activity title strings.
- Expected Output: All stringResource(R.string.xxx) references resolve.
- Time: 15 min

## T1.4 - Drawable Resources
- File: app/src/main/res/drawable/
- Scope: Add ic_stock_logo (app logo for all auth screens), ic_biometric (fingerprint icon for MPIN), right_arrow (navigation icon).
- Expected Output: painterResource(R.drawable.ic_stock_logo) and R.drawable.ic_biometric resolve.
- Time: 10 min

## T1.5 - Raw Resources (Lottie and Audio)
- File: app/src/main/res/raw/
- Scope: Add confetti.json (Lottie party popper animation). Add welcome_chime.mp3 (celebratory chime, about 2 seconds).
- Expected Output: R.raw.confetti and R.raw.welcome_chime resolve correctly.
- Time: 10 min

## T1.6 - Google Fonts Certificate Configuration
- File: app/src/main/res/values/font_certs.xml
- Scope: Create com_google_android_gms_fonts_certs string-array with SHA-256 certificate hashes for GMS fonts provider.
- Expected Output: R.array.com_google_android_gms_fonts_certs resolves.
- Time: 10 min

## T1.7 - PoppinsFamily Font Configuration
- File: app/src/main/java/.../ui/theme/Type.kt
- Scope: Create GoogleFont.Provider, GoogleFont Poppins. Define PoppinsFamily FontFamily with 5 weights: Normal, Bold, Medium, SemiBold, Light. Define Typography with bodyLarge using PoppinsFamily.
- Expected Output: PoppinsFamily importable. Poppins renders in Compose.
- Time: 15 min

## T1.8 - Material3 Theme Configuration
- File: app/src/main/java/.../ui/theme/Theme.kt and Color.kt
- Scope: In Color.kt define theme colors. In Theme.kt create darkColorScheme, lightColorScheme, and StockMarketTheme composable with dynamicColor support.
- Expected Output: StockMarketTheme wraps content with correct Material3 theming.
- Time: 15 min

## T1.9 - API Endpoint Constants and Data Classes
- File: app/src/main/java/.../DedicatedAPI.kt
- Scope: BASE_URL, 8 endpoint constants (LOGIN, SIGNUP, SEND_OTP, UPDATE_STATUS, CHECK_STATUS, FORGOT_PASSWORD, RESET_PASSWORD, COMPANIES). 3 data classes: SignUpResult, StatusResult, ForgotPasswordResult.
- Expected Output: All constants and data classes compile and are importable.
- Time: 15 min

## T1.10 - MainActivity Base Shell
- File: app/src/main/java/.../MainActivity.kt
- Scope: Extend AppCompatActivity. onCreate: installSplashScreen, enableEdgeToEdge, Paper.init, hide system bars (immersive mode), setContent with empty placeholder.
- Expected Output: App launches into full-screen immersive blank screen. Paper DB initialized.
- Time: 15 min

## Parallel Validation: All 10 tickets modify different files. No inter-task dependency.
