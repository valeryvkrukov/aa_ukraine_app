package org.aa.ukraine.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.aa.ukraine.core.data.DefaultMainRepository
import org.aa.ukraine.core.database.Main
import org.aa.ukraine.core.database.MainDao

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultMainRepositoryTest {

    @Test
    fun mains_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultMainRepository(FakeMainDao())

        repository.add("Repository")

        assertEquals(repository.mains.first().size, 1)
    }
}

private class FakeMainDao : MainDao {

    private val data = mutableListOf<Main>()

    override fun getMains(): Flow<List<Main>> = flow {
        emit(data)
    }

    override suspend fun insertMain(item: Main) {
        data.add(0, item)
    }
}
