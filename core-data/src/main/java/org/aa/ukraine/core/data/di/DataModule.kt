package org.aa.ukraine.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.aa.ukraine.core.data.DefaultMainRepository
import org.aa.ukraine.core.data.MainRepository
import org.aa.ukraine.core.data.repository.OfflineFirstReflectionRepository
import org.aa.ukraine.core.data.repository.ReflectionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsMainRepository(
        mainRepository: DefaultMainRepository
    ): MainRepository

    @Binds
    @Singleton
    fun bindReflectionRepository(
        repository: OfflineFirstReflectionRepository
    ): ReflectionRepository
}
