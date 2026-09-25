package org.aa.ukraine.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.aa.ukraine.core.data.DefaultMainRepository
import org.aa.ukraine.core.data.MainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsMainRepository(
        mainRepository: DefaultMainRepository
    ): MainRepository
}
