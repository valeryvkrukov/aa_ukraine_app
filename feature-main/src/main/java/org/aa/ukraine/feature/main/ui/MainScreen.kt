package org.aa.ukraine.feature.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.InternalSerializationApi
import org.aa.ukraine.core.network.model.NetworkReflection
import org.aa.ukraine.core.ui.AAUkraineTheme

@OptIn(InternalSerializationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val reflection by viewModel.dailyReflection.collectAsStateWithLifecycle()
    // Reactively track changes to the system (configuration) language
    val configuration = LocalConfiguration.current
    val currentLang = remember(configuration) {
        configuration.locales.get(0)?.language ?: "uk"
    }

    // Starts lazy loading AI thoughts on startup or when changing locales.
    LaunchedEffect(currentLang) {
        viewModel.loadDailyReflection(currentLang)
    }

    MainScreen(
        reflection = reflection,
        modifier = modifier
    )
}

@OptIn(InternalSerializationApi::class)
@Composable
internal fun MainScreen(
    reflection: NetworkReflection?,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (reflection != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Topic of the Day
                    Text(
                        text = reflection.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFCAB8A2)
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))

                    // Quote from AA literature
                    Text(
                        text = "«${reflection.quote}»",
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    // Source of the quote
                    Text(
                        text = reflection.quoteSource,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 10.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))

                    // Gemini 3 Flash AI Reasoning
                    Text(
                        text = reflection.commentary,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Getting daily reflections from Gemini...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AAUkraineTheme {
        /*MainScreen(
            currentTab = TODO(),
            onTabSelected = TODO(),
            reflection = TODO(),
            modifier = TODO()
        )*/
    }
}
