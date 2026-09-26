package org.aa.ukraine.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.aa.ukraine.core.network.JsoupParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJsoupParser(): JsoupParser {
        return JsoupParser()
    }
}