package org.aa.ukraine.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.aa.ukraine.core.ui.AAUkraineTheme

/**
 * Universal bottom navigation/action bar.
 *
 * @param actions Slot for navigation icons or buttons.
 */
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFCAB8A2),
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
    actions: @Composable RowScope.() -> Unit = {}
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        actions = actions
    )
}

@Preview(showBackground = true)
@Composable
private fun AppBottomBarPreview() {
    AAUkraineTheme {
        AppBottomBar()
    }
}
