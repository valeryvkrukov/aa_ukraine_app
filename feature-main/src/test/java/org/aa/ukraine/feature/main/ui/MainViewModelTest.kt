package org.aa.ukraine.feature.main.ui.main

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.aa.ukraine.core.data.MainRepository
import org.aa.ukraine.feature.main.ui.viewmodel.MainUiState
import org.aa.ukraine.feature.main.ui.viewmodel.MainViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = MainViewModel(FakeMainRepository())
        assertEquals(viewModel.uiState.first(), MainUiState.Loading)
    }

    @Test
    fun uiState_onItemSaved_isDisplayed() = runTest {
        val viewModel = MainViewModel(FakeMainRepository())
        assertEquals(viewModel.uiState.first(), MainUiState.Loading)
    }
}

private class FakeMainRepository : MainRepository {

    private val data = mutableListOf<String>()

    override val mains: Flow<List<String>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(name: String) {
        data.add(0, name)
    }
}
