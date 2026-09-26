package org.aa.ukraine.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.aa.ukraine.core.ui.R
import org.aa.ukraine.core.ui.AAUkraineTheme

/**
 * Universal top panel of the AA Ukraine app.
 *
 * @param title Screen Title Text.
 * @param navigationIcon Left-hand button (e.g. Hamburger Menu or Back arrow).
 * @param actions Set of action buttons on the right (e.g., filter, search, settings).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon ?: {
            Icon(
                painter = painterResource(R.drawable.ic_aa_logo_static),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(52.dp).padding(4.dp)
            )
        },
        actions = actions,
        // Set the signature tones
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview() {
    AAUkraineTheme {
        AppTopBar(
            title = stringResource(R.string.main_top_bar_title),
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_aa_logo_static),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(52.dp).padding(4.dp)
                )
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.main_top_bar_search)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.main_top_bar_menu)
                    )
                }
            }
        )
    }
}
