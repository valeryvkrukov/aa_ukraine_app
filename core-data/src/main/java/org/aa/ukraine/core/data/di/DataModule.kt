package org.aa.ukraine.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.aa.ukraine.core.data.MainRepository
import org.aa.ukraine.core.data.DefaultMainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsMainRepository(
        mainDataRepository: DefaultMainRepository
    ): MainRepository
}

class FakeMainRepository @Inject constructor() : MainRepository {
    override val mainDatas: Flow<List<String>> = flowOf(fakeMains)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

val fakeMains = listOf("One", "Two", "Three")
