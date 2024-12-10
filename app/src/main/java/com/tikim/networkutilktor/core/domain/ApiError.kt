package com.tikim.networkutilktor.core.domain

sealed interface ApiError {
    enum class Remote: ApiError {
        TOO_MANY_REQUEST,
        REQUEST_TIMEOUT,
        SERIALIZATION,
        NO_INTERNET,
        SERVER,
        UNKNOWN
    }
}