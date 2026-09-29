package org.aa.ukraine.feature.main.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel()

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Error(val throwable: Throwable) : MainUiState
    data class Success(val data: List<String>) : MainUiState
}
