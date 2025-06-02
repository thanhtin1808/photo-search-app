package com.android.photosearch.utils

import io.mockk.mockkStatic
import io.mockk.unmockkStatic

internal fun mockkSavedStateHandle() = mockkStatic("androidx.navigation.SavedStateHandleKt")

internal fun unmockkSavedStateHandle() = unmockkStatic("androidx.navigation.SavedStateHandleKt")