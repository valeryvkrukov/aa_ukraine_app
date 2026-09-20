package org.aa.ukraine.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Universal bottom navigation/action bar.
 *
 * @param actions Slot for navigation icons or buttons (accepts any set of IconButton/FloatingActionButton).
 */
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        actions = actions
    )
}