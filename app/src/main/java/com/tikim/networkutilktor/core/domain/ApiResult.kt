package com.tikim.networkutilktor.core.domain

import com.tikim.networkutilktor.Animal
import com.tikim.networkutilktor.animal.mappers.toAnimalDto


data class AnimalDto(val id: String)


fun fetchAnimal(result: ApiResult<Animal, ApiError.Remote>) {

    val stringList = (1..1000).map {
        it.toString()
    }

    result
        .map { animal ->
            println("데이터 타입 변환")
            animal.toAnimalDto()
        }
        .onSuccess { animal ->
            println("성공")
        }
        .onError { error ->
            println("실패")
        }
}


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