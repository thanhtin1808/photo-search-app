package com.android.photosearch.data.repositories.di

import com.android.photosearch.data.repositories.preferences.PreferencesRepositoryImpl
import com.android.photosearch.data.repositories.users.UserRepositoryImpl
import com.era.photosearch.domain.repositories.preferences.PreferencesRepository
import com.era.photosearch.domain.repositories.users.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoriesModule {

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun providePreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}