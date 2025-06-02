package com.era.photosearch.providers.dispatchers.di

import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.era.photosearch.providers.dispatchers.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DispatcherModule {

    @Binds
    @Singleton
    fun bindDispatcherProvider(impl: DispatcherProviderImpl): DispatcherProvider
}