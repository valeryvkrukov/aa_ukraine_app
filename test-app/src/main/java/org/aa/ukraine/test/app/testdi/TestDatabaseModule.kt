package org.aa.ukraine.test.app.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.aa.ukraine.core.data.MainRepository
import org.aa.ukraine.core.data.di.DataModule
import org.aa.ukraine.core.data.di.FakeMainRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeMainRepository
    ): MainRepository
}
