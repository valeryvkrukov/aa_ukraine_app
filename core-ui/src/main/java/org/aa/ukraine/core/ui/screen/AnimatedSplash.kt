package org.aa.ukraine.core.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.nativeCanvas
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
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.aa.ukraine.core.ui.AAUkraineTheme
import kotlin.time.Duration.Companion.milliseconds
import androidx.core.graphics.toColorInt

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
        launch {
            circleAlpha.animateTo(1f, animationSpec = tween(800))
        }
        launch {
            circleScale.animateTo(1f, animationSpec = tween(800))
        }

        delay(300.milliseconds)

        launch {
            triangleAlpha.animateTo(1f, animationSpec = tween(900))
        }
        launch {
            triangleScale.animateTo(1f, animationSpec = tween(900))
        }
        launch {
            triangleRotation.animateTo(0f, animationSpec = tween(900))
        }

        delay(600.milliseconds)

        launch { textAlpha.animateTo(1f, animationSpec = tween(600)) }
        launch { textOffsetY.animateTo(0f, animationSpec = tween(600)) }

        delay(1200.milliseconds)
        onAnimationFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D70B8)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize(0.65f)) {
            val width = size.width
            val height = size.height
            val center = Offset(width / 2f, height / 2f)
            val radius = width.coerceAtMost(height) * 0.48f

            // 1. Rendering the outer AA circle
            if (circleAlpha.value > 0f) {
                drawCircle(
                    color = Color(0xFFFFFC00),
                    radius = radius * circleScale.value,
                    center = center,
                    alpha = circleAlpha.value,
                    style = Stroke(width = 32f)
                )
            }

            // 2. Rendering of a screwing-in triangle
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
                        color = Color(0xFFFFFC00),
                        alpha = triangleAlpha.value,
                        style = Stroke(width = 16f)
                    )
                }
            }

            // 3. Rendering of the letters "AA" (appearing separately, WITHOUT rotating the triangle mesh)
            if (textAlpha.value > 0f) {
                drawContext.canvas.nativeCanvas.apply {
                    val paint = android.graphics.Paint().apply {
                        color = "#FFFC00".toColorInt()
                        textSize = radius * 0.9f
                        isFakeBoldText = true
                        textAlign = android.graphics.Paint.Align.CENTER
                        alpha = (textAlpha.value * 255).toInt()

                        typeface = android.graphics.Typeface.create(
                            android.graphics.Typeface.MONOSPACE,
                            android.graphics.Typeface.BOLD
                        )
                    }

                    // Add textOffsetY.value to the base Y-axis so that the letters "float" from top to bottom
                    val textY = center.y + (paint.textSize / 4.5f) + (radius * 0.2f) + textOffsetY.value

                    drawText(
                        "АА",
                        center.x,
                        textY,
                        paint
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedSplashScreenPreview() {
    AAUkraineTheme {
        AnimatedSplashScreen(onAnimationFinished = {})
    }
}