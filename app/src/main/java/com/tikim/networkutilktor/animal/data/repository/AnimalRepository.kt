package com.tikim.networkutilktor.animal.data.repository

import com.tikim.networkutilktor.animal.data.dto.AnimalDto
import com.tikim.networkutilktor.animal.data.mappers.toAnimal
import com.tikim.networkutilktor.animal.domain.Animal
import com.tikim.networkutilktor.animal.domain.AnimalRepository
import com.tikim.networkutilktor.core.data.safeCall
import com.tikim.networkutilktor.core.domain.ApiError
import com.tikim.networkutilktor.core.domain.ApiResult
import com.tikim.networkutilktor.core.domain.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class AnimalRepositoryImpl(
    private val httpClient: HttpClient
): AnimalRepository {
    override suspend fun fetchAnimal(): ApiResult<List<Animal>, ApiError.Remote> {
        return safeCall<List<AnimalDto>> {
            httpClient.get(
                urlString = "https://www.freetestapi.com/api/v1/animals"
            ) {
//                parameter("key", "value")
            }
        }.map { animalDto ->
            animalDto.map {
                it.toAnimal()
            }
        }
    }
}