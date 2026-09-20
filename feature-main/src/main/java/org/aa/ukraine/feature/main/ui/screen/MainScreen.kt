package org.aa.ukraine.feature.main.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aa.ukraine.core.ui.AAUkraineTheme
import org.aa.ukraine.core.ui.component.AppBottomBar
import org.aa.ukraine.core.ui.component.AppTopBar
import org.aa.ukraine.feature.main.R
import org.aa.ukraine.feature.main.ui.viewmodel.MainUiState
import org.aa.ukraine.feature.main.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is MainUiState.Success) {
        MainScreen(
            items = (items as MainUiState.Success).data,
            onSave = viewModel::addMain,
            modifier = modifier,
        )
    }
}

@Composable
internal fun MainScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = "",
                navigationIcon = {
                    IconButton(onClick = { /* Open Drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.app_topbar_menu))
                    }
                },
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
                IconButton(onClick = { /* To the schedule */ }) { Icon(Icons.Default.DateRange, stringResource(R.string.app_bottombar_groups)) }
                IconButton(onClick = { /* To the diary */ }) { Icon(Icons.Default.Book, stringResource(R.string.app_bottombar_diary)) }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            var name by rememberSaveable { mutableStateOf("") }
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("New Item") },
                    modifier = Modifier.weight(1f),
                )
                Button(
                    onClick = {
                        onSave(name)
                        name = ""
                    },
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    Text("Add")
                }
            }
            LazyColumn {
                items(items) { item ->
                    Text(text = item, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AAUkraineTheme {
        MainScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 340)
@Composable
private fun PortraitPreview() {
    AAUkraineTheme {
        MainScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}