package org.aa.ukraine.feature.main.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.aa.ukraine.feature.main.ui.viewmodel.MainUiState
import org.aa.ukraine.feature.main.ui.viewmodel.MainViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = MainViewModel()
        assertEquals(viewModel.uiState.first(), MainUiState.Loading)
    }
}
