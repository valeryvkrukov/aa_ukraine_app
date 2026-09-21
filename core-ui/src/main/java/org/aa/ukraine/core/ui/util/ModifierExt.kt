package org.aa.ukraine.core.ui.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = this.drawBehind {
    val width = size.width
    val height = size.height
    val strokeWidthPx = strokeWidth.toPx()

    drawLine(
        color = color,
        // The line is drawn along the bottom edge
        start = Offset(x = 0f, y = height - strokeWidthPx / 2),
        end = Offset(x = width, y = height - strokeWidthPx / 2),
        strokeWidth = strokeWidthPx
    )
}

fun Modifier.leftBorder(strokeWidth: Dp, color: Color) = this.drawBehind {
    val height = size.height
    val strokeWidthPx = strokeWidth.toPx()

    drawLine(
        color = color,
        // Vertical line along the left edge
        start = Offset(x = strokeWidthPx / 2, y = 0f),
        end = Offset(x = strokeWidthPx / 2, y = height),
        strokeWidth = strokeWidthPx
    )
}

fun Modifier.rightBorder(strokeWidth: Dp, color: Color) = this.drawBehind {
    val width = size.width
    val height = size.height
    val strokeWidthPx = strokeWidth.toPx()

    drawLine(
        color = color,
        // Vertical line along the right edge
        start = Offset(x = width - strokeWidthPx / 2, y = 0f),
        end = Offset(x = width - strokeWidthPx / 2, y = height),
        strokeWidth = strokeWidthPx
    )
}