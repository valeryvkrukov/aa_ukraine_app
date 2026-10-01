package org.aa.ukraine.core.ui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Listing the sides for applying the border
 */
enum class BorderSide {
    Top, Bottom, Start, End
}

/**
 * Adds a border with the specified thickness, color, and selected sides.
 * Usage example: Modifier.customBorder(strokeWidth = 2.dp, color = Color.Black, BorderSide.Bottom)
 */
fun Modifier.customBorder(
    strokeWidth: Dp = 1.dp,
    color: Color = Color.Black,
    vararg sides: BorderSide = arrayOf(BorderSide.Bottom) // Bottom by default
): Modifier = this.drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    val width = size.width
    val height = size.height

    sides.forEach { side ->
        when (side) {
            BorderSide.Top -> {
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = strokeWidthPx / 2),
                    end = Offset(x = width, y = strokeWidthPx / 2),
                    strokeWidth = strokeWidthPx
                )
            }
            BorderSide.Bottom -> {
                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height - strokeWidthPx / 2),
                    end = Offset(x = width, y = height - strokeWidthPx / 2),
                    strokeWidth = strokeWidthPx
                )
            }
            BorderSide.Start -> {
                drawLine(
                    color = color,
                    start = Offset(x = strokeWidthPx / 2, y = 0f),
                    end = Offset(x = strokeWidthPx / 2, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
            BorderSide.End -> {
                drawLine(
                    color = color,
                    start = Offset(x = width - strokeWidthPx / 2, y = 0f),
                    end = Offset(x = width - strokeWidthPx / 2, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    }
}