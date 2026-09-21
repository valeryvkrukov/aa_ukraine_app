package org.aa.ukraine.feature.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    // TODO: Jsoup / API's implementation
}

sealed interface MainUiState {
    object Loading : MainUiState
    data class Error(val throwable: Throwable) : MainUiState
    data class Success(
        val message: String? = null
    ) : MainUiState
}