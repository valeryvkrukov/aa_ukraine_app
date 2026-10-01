package org.aa.ukraine.feature.main.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.aa.ukraine.core.data.repository.ReflectionRepository
import org.aa.ukraine.core.network.model.NetworkReflection
import org.junit.Assert.assertNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @Test
    fun dailyReflection_initiallyNull() = runTest {
        val viewModel = MainViewModel(FakeReflectionRepository())
        assertNull(viewModel.dailyReflection.first())
    }
}

private class FakeReflectionRepository : ReflectionRepository {
    override fun getDailyReflectionStream(date: String, langCode: String): Flow<NetworkReflection?> {
        return flowOf(null)
    }

    override suspend fun syncReflection(date: String, langCode: String): Boolean {
        return true
    }
}
