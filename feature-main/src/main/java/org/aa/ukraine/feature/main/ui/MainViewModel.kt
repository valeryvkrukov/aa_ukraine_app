package org.aa.ukraine.feature.main.ui

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import org.aa.ukraine.core.data.repository.ReflectionRepository
import org.aa.ukraine.core.network.model.NetworkReflection
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reflectionRepository: ReflectionRepository
) : ViewModel() {
    // Internal state for tracking loading/synchronization with Gemini
    private val _isSyncing = MutableStateFlow(false)
    //val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    // Dynamic parameters of the request to AI: Pair(Date_ISO, Language_Code)
    private val _reflectionParams = MutableStateFlow(Pair("", ""))

    // Jet Flow: automatically switches Room Flow when parameters change
    @OptIn(ExperimentalCoroutinesApi::class, InternalSerializationApi::class)
    val dailyReflection: StateFlow<NetworkReflection?> = _reflectionParams
        .flatMapLatest { (date, lang) ->
            reflectionRepository.getDailyReflectionStream(date, lang)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    /**
     * Triggers a lazy check of the local Room cache and initiates background generation
     * using Gemini 3 Flash in three languages, if an entry for today does not yet exist.
     */
    fun loadDailyReflection(langCode: String) {
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

        // Protection against duplicate identical requests during recompositions
        if (_reflectionParams.value == Pair(todayStr, langCode)) return

        _reflectionParams.value = Pair(todayStr, langCode)

        viewModelScope.launch {
            _isSyncing.value = true
            reflectionRepository.syncReflection(todayStr, langCode)
            _isSyncing.value = false
        }
    }
}
