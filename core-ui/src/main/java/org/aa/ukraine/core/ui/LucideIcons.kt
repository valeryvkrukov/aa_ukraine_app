package org.aa.ukraine.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object LucideIcons {

    private var _home: ImageVector? = null
    val Home: ImageVector
        get() {
            if (_home != null) return _home!!
            _home = ImageVector.Builder(
                name = "Lucide.Home",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(3f, 9f)
                lineTo(12f, 2f)
                lineTo(21f, 9f)
                verticalLineToRelative(11f)
                curveToRelative(0f, 1.1f, -0.9f, 2f, -2f, 2f)
                horizontalLineTo(5f)
                curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
                close()
            }.path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(9f, 22f)
                verticalLineToRelative(-10f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(10f)
            }.build()
            return _home!!
        }

    private var _map: ImageVector? = null
    val Map: ImageVector
        get() {
            if (_map != null) return _map!!
            _map = ImageVector.Builder(
                name = "Lucide.Map",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(3f, 7f)
                lineTo(9f, 4f)
                lineTo(15f, 7f)
                lineTo(21f, 4f)
                verticalLineTo(17f)
                lineTo(15f, 20f)
                lineTo(9f, 17f)
                lineTo(3f, 20f)
                verticalLineTo(7f)
                close()
            }.path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(9f, 4f)
                verticalLineTo(17f)
                moveTo(15f, 7f)
                verticalLineTo(20f)
            }.build()
            return _map!!
        }

    private var _calendarDays: ImageVector? = null
    val CalendarDays: ImageVector
        get() {
            if (_calendarDays != null) return _calendarDays!!
            _calendarDays = ImageVector.Builder(
                name = "Lucide.CalendarDays",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8f, 2f)
                verticalLineToRelative(4f)
                moveTo(16f, 2f)
                verticalLineToRelative(4f)
                moveTo(3f, 10f)
                horizontalLineToRelative(18f)
                moveTo(5f, 4f)
                horizontalLineToRelative(14f)
                arcTo(2f, 2f, 0f, false, true, 21f, 6f)
                verticalLineToRelative(14f)
                arcTo(2f, 2f, 0f, false, true, 19f, 22f)
                horizontalLineTo(5f)
                arcTo(2f, 2f, 0f, false, true, 3f, 20f)
                verticalLineTo(6f)
                arcTo(2f, 2f, 0f, false, true, 5f, 4f)
                close()
            }.path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8f, 14f)
                horizontalLineToRelative(0.01f)
                moveTo(12f, 14f)
                horizontalLineToRelative(0.01f)
                moveTo(16f, 14f)
                horizontalLineToRelative(0.01f)
                moveTo(8f, 18f)
                horizontalLineToRelative(0.01f)
                moveTo(12f, 18f)
                horizontalLineToRelative(0.01f)
                moveTo(16f, 18f)
                horizontalLineToRelative(0.01f)
            }.build()
            return _calendarDays!!
        }

    private var _notebookPen: ImageVector? = null
    val NotebookPen: ImageVector
        get() {
            if (_notebookPen != null) return _notebookPen!!
            _notebookPen = ImageVector.Builder(
                name = "Lucide.NotebookPen",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(13.4f, 2f)
                horizontalLineTo(6f)
                arcTo(2f, 2f, 0f, false, false, 4f, 4f)
                verticalLineTo(20f)
                arcTo(2f, 2f, 0f, false, false, 6f, 22f)
                horizontalLineTo(18f)
                arcTo(2f, 2f, 0f, false, false, 20f, 20f)
                verticalLineTo(10.6f)
            }.path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(2f, 6f)
                horizontalLineTo(4f)
                moveTo(2f, 10f)
                horizontalLineTo(4f)
                moveTo(2f, 14f)
                horizontalLineTo(4f)
                moveTo(2f, 18f)
                horizontalLineTo(4f)
            }.path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(18.378f, 5.626f)
                lineTo(11.13f, 12.874f)
                arcTo(2f, 2f, 0f, false, false, 10.624f, 13.728f)
                lineToRelative(-0.99f, 2.971f)
                arcTo(0.5f, 0.5f, 0f, false, false, 10.267f, 17.33f)
                lineToRelative(2.971f, -0.99f)
                arcTo(2f, 2f, 0f, false, false, 14.092f, 15.834f)
                lineTo(21.34f, 8.586f)
                arcToRelative(2.121f, 2.121f, 0f, false, false, -2.962f, -2.96f)
                close()
            }.build()
            return _notebookPen!!
        }

    private var _circleHelp: ImageVector? = null
    val CircleHelp: ImageVector
        get() {
            if (_circleHelp != null) return _circleHelp!!
            _circleHelp = ImageVector.Builder(
                name = "Lucide.CircleHelp",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12f, 2f)
                arcTo(10f, 10f, 0f, true, false, 22f, 12f)
                arcTo(10f, 10f, 0f, false, false, 12f, 2f)
                close()
            }.path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(9.09f, 9f)
                arcTo(3f, 3f, 0f, false, true, 14.92f, 10f)
                curveTo(14.92f, 12f, 11.92f, 13f, 11.92f, 13f)
                moveTo(12f, 17f)
                horizontalLineToRelative(0.01f)
            }.build()
            return _circleHelp!!
        }
}
