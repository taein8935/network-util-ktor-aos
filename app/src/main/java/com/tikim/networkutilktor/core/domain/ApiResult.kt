package com.tikim.networkutilktor.core.domain


sealed interface ApiResult<out T, out E: ApiError> {
    data class Success<T>(val data: T) : ApiResult<T, Nothing>
    data class Error<E: ApiError>(val error: E) : ApiResult<Nothing, E>
}

inline fun <T, E: ApiError, R> ApiResult<T, E>.map(map: (T) -> R): ApiResult<R, E> {
    return when (this) {
        is ApiResult.Error -> {
            val error = this.error
            ApiResult.Error(error)
        }
        is ApiResult.Success -> {
            val data = this.data
            val newData = map(data)
            ApiResult.Success(newData)
        }
    }
}

inline fun <T, E: ApiError> ApiResult<T, E>.onSuccess(action: (T) -> Unit): ApiResult<T, E> {
    return when (this) {
        is ApiResult.Error -> {
            this
        }
        is ApiResult.Success -> {
            val data = this.data
            action(data)
            this
        }
    }
}

inline fun <T, E: ApiError> ApiResult<T, E>.onError(action: (E) -> Unit): ApiResult<T, E> {
    return when (this) {
        is ApiResult.Error -> {
            val error = this.error
            action(error)
            this
        }
        is ApiResult.Success -> {
            this
        }
    }
}