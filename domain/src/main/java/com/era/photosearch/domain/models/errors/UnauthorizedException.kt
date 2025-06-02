package com.era.photosearch.domain.models.errors

data class UnauthorizedException(
    override val message: String = "Unauthorized",
) : RuntimeException()