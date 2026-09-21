package org.aa.ukraine.feature.main.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aa.ukraine.core.ui.AAUkraineTheme
import org.aa.ukraine.feature.main.R
import org.aa.ukraine.core.ui.component.AppBottomBar
import org.aa.ukraine.core.ui.component.AppTopBar
import org.aa.ukraine.feature.main.ui.viewmodel.MainUiState
import org.aa.ukraine.feature.main.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    onScheduleClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        uiState = uiState,
        onScheduleClick = onScheduleClick,
        modifier = modifier,
    )
}

@Composable
internal fun MainScreen(
    uiState: MainUiState,
    onScheduleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(org.aa.ukraine.core.ui.R.string.main_topbar_title),
                actions = {
                    IconButton(onClick = { /* Open city filter */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = stringResource(R.string.app_topbar_filter))
                    }
                    IconButton(onClick = { /* Search for a group */ }) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.app_topbar_search))
                    }
                },
            )
        },
        bottomBar = {
            AppBottomBar {
                // Icons for switching application sections
                IconButton(onClick = { onScheduleClick() }) {
                    Icon(
                        Icons.Default.DateRange,
                        stringResource(R.string.app_bottombar_groups)
                    )
                }
                IconButton(onClick = { /* To the diary */ }) { Icon(Icons.Default.Book, stringResource(R.string.app_bottombar_diary)) }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            // TODO: MainScreen contents implementation
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AAUkraineTheme {
        MainScreen(
            onScheduleClick = {},
            modifier = Modifier
        )
    }
}