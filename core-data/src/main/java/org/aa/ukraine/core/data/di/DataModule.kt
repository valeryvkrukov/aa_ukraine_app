package org.aa.ukraine.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import org.aa.ukraine.core.AppInitializer
import org.aa.ukraine.core.data.DatabaseInitializer
import org.aa.ukraine.core.data.MeetingRepository
import org.aa.ukraine.core.data.OfflineFirstMeetingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMeetingRepository(
        offlineFirstMeetingRepository: OfflineFirstMeetingRepository
    ): MeetingRepository

    @Binds
    @IntoSet
    abstract fun bindDatabaseInitializer(
        impl: DatabaseInitializer
    ): AppInitializer
}
