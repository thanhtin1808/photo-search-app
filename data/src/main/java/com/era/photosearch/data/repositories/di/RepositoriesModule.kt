package com.era.photosearch.data.repositories.di

import com.android.photosearch.data.repositories.preferences.PreferencesRepositoryImpl
import com.era.photosearch.data.repositories.users.PhotoRepositoryImpl
import com.era.photosearch.domain.repositories.preferences.PreferencesRepository
import com.era.photosearch.domain.repositories.users.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoriesModule {

    @Binds
    fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    fun providePreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}