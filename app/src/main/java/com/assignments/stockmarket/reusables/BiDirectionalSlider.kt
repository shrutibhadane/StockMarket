package com.assignments.stockmarket.reusables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignments.stockmarket.R
import com.assignments.stockmarket.ui.theme.PoppinsFamily
import com.assignments.stockmarket.utils.AppTextStyles
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Vertical **speed-controller** slider (joystick style, 3/4 height).
 *
 * The white thumb starts at the **CENTER** — the boundary between the green
 * (upper) and red (lower) halves.
 *
 * • Pull the thumb **UP** → the quantity value starts **increasing**.
 *   The further up you pull, the **faster** it increases.
 * • Pull the thumb **DOWN** → the quantity value starts **decreasing**.
 *   The further down you pull, the **faster** it decreases.
 * • **Release** the thumb → it stays in place and the value stops changing.
 */
@Composable
fun BiDirectionalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    maxValue: Float = 100f,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val thumbRadius = 24.dp
    val thumbRadiusPx = with(density) { thumbRadius.toPx() }
    val trackWidthDp = 80.dp

    var trackHeightPx by remember { mutableFloatStateOf(0f) }
    var thumbYPx by remember { mutableFloatStateOf(Float.NaN) }
    var isDragging by remember { mutableStateOf(false) }

    val currentValue by rememberUpdatedState(value)
    val currentOnChange by rememberUpdatedState(onValueChange)

    // ── Continuous accumulator: runs every 50 ms while dragging ──
    LaunchedEffect(Unit) {
        while (true) {
            delay(50L)
            val tH = trackHeightPx
            val tY = thumbYPx
            if (isDragging && tH > 0f && !tY.isNaN()) {
                val ur = (tH - thumbRadiusPx * 2).coerceAtLeast(1f)
                val cY = tH / 2f
                val disp = ((tY - cY) / (ur / 2f)).coerceIn(-1f, 1f)
                val speed = -disp
                if (abs(speed) > 0.05f) {
                    val maxSpeed = 3f
                    val delta = speed * maxSpeed
                    val newVal = (currentValue + delta).coerceIn(0f, maxValue)
                    currentOnChange(newVal)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .width(trackWidthDp)
            .fillMaxHeight(0.75f)
            .padding(vertical = 30.dp)
            .onSizeChanged { size ->
                trackHeightPx = size.height.toFloat()
                thumbYPx = size.height.toFloat() / 2f
            }
    ) {
        // ── "Buy" label at top ──
        Text(
            text = stringResource(R.string.increase),
            style = AppTextStyles.bold(12, colorResource(R.color.text_success)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-22).dp)
        )

        // ── Track (pill, green top ↔ red bottom, vivid at center) ──
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x1A4CAF50),
                            Color(0x664CAF50),
                            Color(0xFF4CAF50),
                            Color(0xFF4CAF50),
                            Color(0xFFE53935),
                            Color(0xFFE53935),
                            Color(0x66E53935),
                            Color(0x1AE53935),
                        )
                    )
                )
        )

        // ── "Sell" label at bottom ──
        Text(
            text = "Decrease",
            style = AppTextStyles.bold(12, colorResource(R.color.text_error)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 22.dp)
        )

        // ── Thumb (white circle, shows current qty) ──
        if (!thumbYPx.isNaN()) {
            Box(
                modifier = Modifier
                    .size(thumbRadius * 2)
                    .offset {
                        IntOffset(
                            x = ((trackWidthDp.toPx() - thumbRadius.toPx() * 2) / 2).roundToInt(),
                            y = (thumbYPx - thumbRadiusPx).roundToInt()
                        )
                    }
                    .shadow(6.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onDragStart = { isDragging = true },
                            onDragEnd = {
                                isDragging = false
                                thumbYPx = trackHeightPx / 2f   // snap back to center
                            },
                            onDragCancel = {
                                isDragging = false
                                thumbYPx = trackHeightPx / 2f   // snap back to center
                            },
                            onVerticalDrag = { change, dragAmount ->
                                change.consume()
                                thumbYPx = (thumbYPx + dragAmount)
                                    .coerceIn(thumbRadiusPx, trackHeightPx - thumbRadiusPx)
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.roundToInt().toString(),
                    style = AppTextStyles.bold(12, colorResource(R.color.black)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Full-screen overlay that darkens the background and shows
 * the bi-directional slider + action label (BUY / SELL) + Confirm button.
 *
 * @param visible       whether the overlay is shown
 * @param actionLabel   "BUY" or "SELL"
 * @param value         current slider value (quantity)
 * @param onValueChange slider drag callback
 * @param onConfirm     called when "Confirm" is tapped with the final value
 * @param onDismiss     called when tapping the scrim to close without confirming
 */
@Composable
fun SliderOverlay(
    visible: Boolean,
    actionLabel: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    onConfirm: (Float) -> Unit,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xE6000000)),      // darker scrim — no tap-to-dismiss
            contentAlignment = Alignment.Center
        ) {
            // ── Close (X) button at top-right ──
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 48.dp, end = 20.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.80f)
                    .width(130.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // ── Action label at top ──
                Text(
                    text = actionLabel,
                    style = AppTextStyles.bold(20),
                    color = if (actionLabel == "BUY") Color(0xFF4CAF50) else Color(0xFFE53935),
                    modifier = Modifier.padding(top = 4.dp)
                )

                // ── Slider in the middle ──
                BiDirectionalSlider(
                    value = value,
                    onValueChange = onValueChange,
                    maxValue = 100f,
                    modifier = Modifier.weight(1f)
                )

                // ── Qty label ──
                Text(
                    text = "Qty: ${value.roundToInt()}",
                    style = AppTextStyles.semiBold(14),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // ── Confirm button ──
                Button(
                    onClick = { onConfirm(value) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (actionLabel == "BUY") Color(0xFF4CAF50) else Color(0xFFE53935)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Confirm",
                        style = AppTextStyles.bold(16),
                    )
                }
            }
        }
    }
}

