package com.era.photosearch.domain.usecases.preferences

import com.era.photosearch.domain.repositories.preferences.PreferencesRepository
import javax.inject.Inject

class SetFirstRunUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke(isFirstRun: Boolean) = preferencesRepository.setFirstRun(isFirstRun)
}