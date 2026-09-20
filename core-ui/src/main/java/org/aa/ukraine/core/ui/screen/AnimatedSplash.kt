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

    LaunchedEffect(key1 = true) {
        // Phase 1: Emergence and expansion of the outer circle
        launch {
            circleAlpha.animateTo(1f, animationSpec = tween(800))
        }
        launch {
            circleScale.animateTo(1f, animationSpec = tween(800))
        }

        // Slight animation overlap for smoothness
        delay(400.milliseconds)

        // Phase 2: The triangle "screws in" and assembles inside the circle
        launch {
            triangleAlpha.animateTo(1f, animationSpec = tween(900))
        }
        launch {
            triangleScale.animateTo(1f, animationSpec = tween(900))
        }
        launch {
            triangleRotation.animateTo(0f, animationSpec = tween(900))
        }

        delay(1200.milliseconds) // Keep the assembled logo on the screen
        onAnimationFinished() // Signal to switch to the main screen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D70B8)), // The signature blue background of aa.org.ua
        contentAlignment = Alignment.Center
    ) {
        // Tracing the logo geometry
        Canvas(modifier = Modifier.fillMaxSize(0.4f)) {
            val width = size.width
            val height = size.height
            val center = Offset(width / 2f, height / 2f)
            val radius = width.coerceAtMost(height) * 0.4f

            // 1. Draw the outer circle, taking animation into account
            if (circleAlpha.value > 0f) {
                drawCircle(
                    color = Color.White,
                    radius = radius * circleScale.value,
                    center = center,
                    alpha = circleAlpha.value,
                    style = Stroke(width = 12f)
                )
            }

            // 2. Draw a triangle, taking rotation and scale into account
            if (triangleAlpha.value > 0f) {
                val trianglePath = Path().apply {
                    // Top corner of a triangle
                    moveTo(center.x, center.y - radius * 0.8f)
                    // Bottom right corner
                    lineTo(center.x + radius * 0.7f, center.y + radius * 0.5f)
                    // Lower left corner
                    lineTo(center.x - radius * 0.7f, center.y + radius * 0.5f)
                    close()
                }

                // We use the built-in shift, rotation, and scaling of the canvas itself:
                withTransform({
                    // Move the transformation point to the center of the logo
                    translate(left = 0f, top = 0f)
                    // Rotate around the center
                    rotate(degrees = triangleRotation.value, pivot = center)
                    // Scale relative to the center
                    scale(scaleX = triangleScale.value, scaleY = triangleScale.value, pivot = center)
                }) {
                    // Draw a triangle inside the transformed area
                    drawPath(
                        path = trianglePath,
                        color = Color.White,
                        alpha = triangleAlpha.value,
                        style = Stroke(width = 12f)
                    )
                }
            }
        }
    }
}