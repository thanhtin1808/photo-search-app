package com.era.photosearch.domain.usecases.preferences

import com.era.photosearch.domain.repositories.preferences.PreferencesRepository
import javax.inject.Inject

class IsFirstRunUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke() = preferencesRepository.isFirstRun()
}