package org.aa.ukraine.feature.main.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.InternalSerializationApi
import org.aa.ukraine.core.network.model.NetworkReflection
import org.aa.ukraine.core.ui.R as CoreR
import org.aa.ukraine.core.ui.modifier.BorderSide
import org.aa.ukraine.core.ui.modifier.customBorder
import org.aa.ukraine.core.ui.modifier.shimmerEffect
import java.util.Date
import androidx.compose.ui.platform.LocalLocale

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
        val rawLang = configuration.locales.get(0)?.language ?: "uk"
        if (rawLang.length >= 2) rawLang.substring(0, 2).lowercase() else "uk"
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
    Card(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.04f)
        ),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        if (reflection != null) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Topic of the Day
                Text(
                    text = stringResource(
                        CoreR.string.topic_of_the_day,
                        SimpleDateFormat(
                            "EEE, MMM d",
                            LocalLocale.current.platformLocale
                        ).format(Date())
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = reflection.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )

                // Quote from AA literature
                Text(
                    text = reflection.quote,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                // Source of the quote
                Text(
                    text = reflection.quoteSource,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 10.dp,
                            bottom = 16.dp
                        )
                        .customBorder(
                            strokeWidth = 4.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            BorderSide.Start
                        )
                        .padding(start = 8.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )

                Text(
                    text = reflection.commentary,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        } else {
            LoadShimmer()
        }
    }
}

@Composable
internal fun LoadShimmer() {
    val density = LocalDensity.current

    val titleLargeSp = MaterialTheme.typography.titleLarge.fontSize
    val titleMediumSp = MaterialTheme.typography.titleMedium.fontSize
    val bodyLargeSp = MaterialTheme.typography.bodyLarge.fontSize
    val bodySmallSp = MaterialTheme.typography.bodySmall.fontSize

    val titleHeightDp = remember(density, titleLargeSp) {
        if (titleLargeSp.isSp) {
            (titleLargeSp.value * density.fontScale).dp
        } else {
            24.dp
        }
    }
    val titleMediumDp = remember(density, titleMediumSp) {
        if (titleMediumSp.isSp) {
            (titleMediumSp.value * density.fontScale).dp
        } else {
            20.dp
        }
    }
    val bodyHeighLargeDp = remember(density, bodyLargeSp) {
        if (bodyLargeSp.isSp) {
            (bodyLargeSp.value * density.fontScale).dp
        } else {
            16.dp
        }
    }
    val bodyHeightSmallDp = remember(density, bodySmallSp) {
        if (bodySmallSp.isSp) {
            (bodySmallSp.value * density.fontScale).dp
        } else {
            16.dp
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        // Shimmer for the topic title
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(titleHeightDp)
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(titleMediumDp)
            .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Shimmer for the quote block
        (1..5).iterator().forEach { _ ->
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(bodyHeighLargeDp).shimmerEffect())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(16.dp)
            .padding(start = 8.dp)
            .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Shimmer for a long, central reflection
        (1..8).iterator().forEach { _ ->
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(bodyHeightSmallDp).shimmerEffect())
        }
    }
}
