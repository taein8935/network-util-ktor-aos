package com.tikim.networkutilktor.core

import com.tikim.networkutilktor.Animal


data class AnimalDto(val id: String)
fun Animal.toAnimalDto(): AnimalDto {
    return AnimalDto(
        id = ""
    )
}

fun fetchAnimal(result: ApiResult<Animal, Int>) {

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


sealed interface ApiResult<out T, out E> {
    data class Success<T>(val data: T) : ApiResult<T, Nothing>
    data class Error<E>(val error: E) : ApiResult<Nothing, E>
}

inline fun <T, E, R> ApiResult<T, E>.map(map: (T) -> R): ApiResult<R, E> {
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

inline fun <T, E> ApiResult<T, E>.onSuccess(action: (T) -> Unit): ApiResult<T, E> {
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

inline fun <T, E> ApiResult<T, E>.onError(action: (E) -> Unit): ApiResult<T, E> {
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