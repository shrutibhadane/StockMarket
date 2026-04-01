package com.assignments.stockmarket.authorization

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.assignments.stockmarket.R
import com.assignments.stockmarket.navigation.Routes.DASHBOARD
import com.assignments.stockmarket.navigation.Routes.WELCOME
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {

    val context = LocalContext.current

    // Lottie confetti animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 3,
        speed = 1.2f
    )

    // Staggered visibility flags
    var showConfetti by remember { mutableStateOf(false) }
    var showWelcome by remember { mutableStateOf(false) }
    var showInto by remember { mutableStateOf(false) }
    var showTradeSphere by remember { mutableStateOf(false) }

    // 🔊 Play welcome chime for 2 seconds
    DisposableEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.welcome_chime)
        mediaPlayer?.start()

        // Stop after 2 seconds
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer.stop()
            }
            mediaPlayer?.release()
        }, 2000L)

        onDispose {
            handler.removeCallbacksAndMessages(null)
            try {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer.stop()
                }
                mediaPlayer?.release()
            } catch (_: Exception) { }
        }
    }

    LaunchedEffect(Unit) {
        // Party poppers burst first
        showConfetti = true
        delay(600)

        // "Welcome" appears
        showWelcome = true
        delay(700)

        // "into" appears
        showInto = true
        delay(700)

        // "Trade Sphere" appears
        showTradeSphere = true

        // Wait remaining time then navigate to dashboard (total ~4s)
        delay(2000)
        navController.navigate(DASHBOARD) {
            popUpTo(WELCOME) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_primary)),
        contentAlignment = Alignment.Center
    ) {

        // 🎉 Confetti animation at top-left
        AnimatedVisibility(
            visible = showConfetti,
            enter = fadeIn(animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(250.dp)
            )
        }

        // Centered text content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // "Welcome"
            AnimatedVisibility(
                visible = showWelcome,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    animationSpec = tween(500),
                    initialOffsetY = { it / 2 }
                )
            ) {
                Text(
                    text = "Welcome",
                    color = colorResource(R.color.white),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // "into"
            AnimatedVisibility(
                visible = showInto,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    animationSpec = tween(500),
                    initialOffsetY = { it / 2 }
                )
            ) {
                Text(
                    text = "into",
                    color = colorResource(R.color.text_primary),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // "Trade Sphere"
            AnimatedVisibility(
                visible = showTradeSphere,
                enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
                    animationSpec = tween(600),
                    initialOffsetY = { it / 2 }
                )
            ) {
                Text(
                    text = "Trade Sphere",
                    color = colorResource(R.color.white),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
