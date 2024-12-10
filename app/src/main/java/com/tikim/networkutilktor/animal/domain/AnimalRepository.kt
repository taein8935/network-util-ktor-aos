package com.tikim.networkutilktor.animal.domain

import com.tikim.networkutilktor.core.domain.ApiError
import com.tikim.networkutilktor.core.domain.ApiResult

interface AnimalRepository {
    suspend fun fetchAnimal(): ApiResult<List<Animal>, ApiError.Remote>
}