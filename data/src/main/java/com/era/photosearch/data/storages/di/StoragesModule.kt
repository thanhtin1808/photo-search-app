package com.android.photosearch.data.storages.di

import com.android.photosearch.data.storages.datastore.preferences.PreferencesDataStore
import com.android.photosearch.data.storages.datastore.preferences.PreferencesDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface StoragesModule {

    @Binds
    fun providePreferencesDataStore(impl: PreferencesDataStoreImpl): PreferencesDataStore
}