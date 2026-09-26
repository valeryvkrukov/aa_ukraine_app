package org.aa.ukraine.core.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AnimatedSplashScreen(onAnimationFinished: () -> Unit) {
    val circleAlpha = remember { Animatable(0f) }
    val circleScale = remember { Animatable(0.6f) }

    val triangleAlpha = remember { Animatable(0f) }
    val triangleScale = remember { Animatable(0.3f) }
    val triangleRotation = remember { Animatable(-90f) }

    val textAlpha = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(-50f) }

    LaunchedEffect(key1 = true) {
        // Phase 1: Appearance of the outer circle of the AA logo
        launch { circleAlpha.animateTo(1f, animationSpec = tween(800)) }
        launch { circleScale.animateTo(1f, animationSpec = tween(800)) }

        delay(300.milliseconds)

        // Phase 2: Screwing in the 12-Step program triangle
        launch { triangleAlpha.animateTo(1f, animationSpec = tween(800)) }
        launch { triangleScale.animateTo(1f, animationSpec = tween(800)) }
        launch { triangleRotation.animateTo(0f, animationSpec = tween(800)) }

        delay(600.milliseconds)

        // Phase 3: Smooth lowering of the letters "AA" from above
        launch { textAlpha.animateTo(1f, animationSpec = tween(600)) }
        launch { textOffsetY.animateTo(0f, animationSpec = tween(600)) }

        delay(1400.milliseconds)
        onAnimationFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D70B8)), // The signature blue color of AA Ukraine
        contentAlignment = Alignment.Center
    ) {
        // CACHING FONTS: We load the Sans Serif typeface into memory exactly once
        val customTypeface = remember {
            android.graphics.Typeface.create(
                android.graphics.Typeface.SANS_SERIF,
                android.graphics.Typeface.BOLD
            )
        }

        val textPaint = remember(customTypeface) {
            android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                isFakeBoldText = true
                textAlign = android.graphics.Paint.Align.CENTER
                typeface = customTypeface
            }
        }

        Canvas(modifier = Modifier.fillMaxSize(0.65f)) {
            val width = size.width
            val height = size.height
            val center = Offset(width / 2f, height / 2f)
            val radius = width.coerceAtMost(height) * 0.48f

            // 1. Drawing a Circle
            if (circleAlpha.value > 0f) {
                drawCircle(
                    color = Color.White,
                    radius = radius * circleScale.value,
                    center = center,
                    alpha = circleAlpha.value,
                    style = Stroke(width = 16f)
                )
            }

            // 2. Rendering a triangle with rotation and scaling
            withTransform({
                rotate(degrees = triangleRotation.value, pivot = center)
                scale(scaleX = triangleScale.value, scaleY = triangleScale.value, pivot = center)
            }) {
                if (triangleAlpha.value > 0f) {
                    val trianglePath = Path().apply {
                        moveTo(center.x, center.y - radius * 0.96f)
                        lineTo(center.x + radius * 0.83f, center.y + radius * 0.48f)
                        lineTo(center.x - radius * 0.83f, center.y + radius * 0.48f)
                        close()
                    }
                    drawPath(
                        path = trianglePath,
                        color = Color.White,
                        alpha = triangleAlpha.value,
                        style = Stroke(width = 16f)
                    )
                }
            }

            // 3. Fast rendering of the letters "AA" using a native brush
            if (textAlpha.value > 0f) {
                drawContext.canvas.nativeCanvas.apply {
                    textPaint.apply {
                        textSize = radius * 0.8f
                        alpha = (textAlpha.value * 255).toInt()
                    }

                    val textY = center.y + (textPaint.textSize / 3.5f) + (radius * 0.15f) + textOffsetY.value

                    drawText("АА", center.x, textY, textPaint)
                }
            }
        }
    }
}