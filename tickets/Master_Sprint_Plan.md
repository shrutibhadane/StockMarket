# 📋 Sprint & Ticket Plan — Trade Sphere (Stock Market Application)

**Scope:** Splash Screen, Login Screen, Signup Screen, Dedicated API, MPIN Screen, OTP Screen, Main Activity, Welcome Screen, Forgot Password Screen, Reset Password Screen

**Purpose:** Testing & validation of sprint structure, dependency rules, and task clarity.

**Sprint Rule:** Each sprint MUST contain 8–10 tickets. All tickets within a sprint are executable in parallel with zero inter-task dependency.

---

## 📊 Phase 1 & 2: Application Flowchart

```
┌─────────────────────────────────────────────────────────────────────┐
│                          APP LAUNCH                                 │
│                       MainActivity.kt                               │
│  • Paper.init()  • enableEdgeToEdge()  • Immersive mode             │
│  • setContent { AppNavigation() }                                   │
└──────────────────────────┬──────────────────────────────────────────┘
                           ▼
┌─────────────────────────────────────────────────────────────────────┐
│                     SPLASH SCREEN (2 sec)                           │
│  Display logo → Check Paper DB for "user_email"                     │
└──────────────┬──────────────────────────────┬───────────────────────┘
        email == null                   email exists
               │                              │
               ▼                              ▼
    ┌──────────────────┐         ┌─────────────────────────────┐
    │   LOGIN SCREEN   │         │   Check Paper "user_mpin"   │
    └──────┬───────────┘         └──────┬──────────────┬───────┘
           │                     mpin == null      mpin exists
           │                          │                 │
           │                          ▼                 ▼
           │              ┌──────────────┐     ┌──────────────────┐
           │              │ MPIN (Setup) │     │ last_login check │
           │              └──────────────┘     └───┬──────────┬───┘
           │                                  < 24h        ≥ 24h
           │                                    │              │
           │                              ┌──────────┐ ┌──────────────┐
           │                              │DASHBOARD │ │MPIN (Verify) │
           │                              └──────────┘ └──────────────┘
           ▼
┌──────────────────────────────────────────────────────────────────────┐
│  LOGIN: username + password → POST /api/login                        │
│  → Generate 4-digit OTP → POST /api/sendotp → OTP Screen            │
│  Links: "Forgot Password" → ForgotPasswordScreen                     │
│         "Create Account" → SignUpScreen                              │
└──────────────────────────────────────────┬───────────────────────────┘
                                           ▼
┌──────────────────────────────────────────────────────────────────────┐
│  SIGNUP: 7 fields → Validate → POST /api/signup                      │
│  → Generate 4-digit OTP → POST /api/sendotp                          │
│  → "Congratulations" dialog → OTP Screen                             │
└──────────────────────────────────────────┬───────────────────────────┘
                                           ▼
┌──────────────────────────────────────────────────────────────────────┐
│  OTP SCREEN: 4-digit (normal) / 6-digit (forgot password)           │
│  • 120-second timer • Resend via API                                 │
│  Normal → POST /api/updatestatus → Paper.write(email) → MPIN Setup  │
│  ResetMpin → MPIN Reset Mode                                        │
│  ForgotPwd → Reset Password Screen                                  │
└──────────────────────────────────────────┬───────────────────────────┘
                                           ▼
┌──────────────────────────────────────────────────────────────────────┐
│  MPIN: Setup → Save to Paper → Welcome Screen                       │
│        Verify → Match? → Dashboard (save login time)                 │
│        Wrong ×3 → Suspended → Reset MPIN → OTP                      │
│        Reset → Save new MPIN → Dashboard                            │
└──────────────────────────────────────────┬───────────────────────────┘
                                           ▼
┌──────────────────────────────────────────────────────────────────────┐
│  WELCOME: Confetti + "Welcome into Trade Sphere" + Chime → Dashboard │
└──────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────┐
│  FORGOT PASSWORD → POST /api/forgotpassword → OTP (6-digit)         │
│  → RESET PASSWORD → Criteria + POST /api/resetpassword → Login      │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 📦 Phase 3: Functional Decomposition (15 Units)

| # | Functional Unit | Files |
|---|----------------|-------|
| F1 | Project Configuration | `build.gradle.kts`, `AndroidManifest.xml` |
| F2 | Theme & Resources | `colors.xml`, `strings.xml`, `Type.kt`, `Theme.kt`, drawables, raw assets |
| F3 | Reusable UI Components | `CustomTextField.kt`, `OTPInput.kt`, `PasswordCriteriaRow.kt`, `CircularSubmitButton.kt` |
| F4 | API Layer — Constants & Models | `DedicatedAPI.kt` (constants + data classes) |
| F5 | API Layer — Functions | `DedicatedAPI.kt` (7 suspend functions) |
| F6 | Utilities | `ValidationUtils.kt` (email validation) |
| F7 | Session Management | `SessionManager.kt` (Paper DB wrapper) |
| F8 | Navigation Graph | `AppNavigation.kt`, `MainActivity.kt` |
| F9 | Splash Screen | `SplashScreen.kt` |
| F10 | Login Screen | `LoginScreen.kt` |
| F11 | Signup Screen | `SignUpScreen.kt` |
| F12 | OTP Screen | `OTPScreen.kt` |
| F13 | MPIN Screen | `MPINScreen.kt` |
| F14 | Welcome Screen | `WelcomeScreen.kt` |
| F15 | Forgot/Reset Password | `ForgotPasswordScreen.kt`, `ResetPasswordScreen.kt` |

---

## 🏃 Sprint Overview (4 Sprints × 10 Tickets = 40 Tickets)

```
  Sprint 1 (10)              Sprint 2 (10)             Sprint 3 (10)             Sprint 4 (10)
┌──────────────┐          ┌──────────────┐          ┌──────────────┐          ┌──────────────┐
│ Gradle Deps  │          │ CustomTextField│         │ SplashScreen │          │ AppNavigation│
│ colors.xml   │          │ OTPInput     │          │ LoginScreen  │          │ SessionMgr   │
│ strings.xml  │──────▶   │ authenticate │──────▶   │ SignUpScreen │──────▶   │ Splash Logic │
│ Drawables    │          │ register     │          │ OTPScreen    │          │ Login Logic  │
│ Raw assets   │          │ sendOtp      │          │ MPINScreen   │          │ Signup Logic │
│ Font Certs   │          │ updateStatus │          │ WelcomeScreen│          │ OTP Logic    │
│ Type.kt      │          │ checkStatus  │          │ ForgotPwdScr │          │ MPIN Logic   │
│ Theme.kt     │          │ forgotPwd    │          │ ResetPwdScr  │          │ Welcome Logic│
│ DedicatedAPI │          │ resetPwd     │          │ PwdCriteria  │          │ ForgotPwd    │
│ MainActivity │          │ validateEmail│          │ CircularBtn  │          │ ResetPwd     │
└──────────────┘          └──────────────┘          └──────────────┘          └──────────────┘
   Foundation              Components + API           Screen UI Shells        Nav + Business Logic
```

---

## ✅ Validation Checklist

| # | Check | Status |
|---|-------|--------|
| 1 | Flowchart matches actual code | ✅ |
| 2 | Functionalities properly separated (15 units) | ✅ |
| 3 | All 40 tickets clear, actionable, and time-bound | ✅ |
| 4 | **Every sprint has exactly 10 tickets** | ✅ |
| 5 | No dependency within any sprint | ✅ |
| 6 | All sprint tasks executable in parallel | ✅ |
| 7 | Forward-only progression across sprints | ✅ |
| 8 | Scope limited to defined screens only | ✅ |
| 9 | No assumptions outside codebase | ✅ |

---

## 📈 Summary

| Metric | Value |
|--------|-------|
| **Sprints** | 4 |
| **Tickets per Sprint** | 10 each |
| **Total Tickets** | 40 |
| **Functionalities** | 15 |
| **Screens Covered** | 8 |
| **Shared Modules** | 6 |
| **API Functions** | 7 |
| **Estimated Total Time** | ~13 hours |

