package com.tikim.networkutilktor.core.data

import com.tikim.networkutilktor.core.domain.ApiError
import com.tikim.networkutilktor.core.domain.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                this.socketTimeoutMillis = 20_000L
                this.requestTimeoutMillis = 30_000L
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): ApiResult<T, ApiError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return ApiResult.Error(ApiError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return ApiResult.Error(ApiError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return ApiResult.Error(ApiError.Remote.UNKNOWN)
    }

    return when (response.status.value) {
        in 200..299 -> {
            try {
                ApiResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                ApiResult.Error(ApiError.Remote.SERIALIZATION)
            }
        }
        408 -> ApiResult.Error(ApiError.Remote.REQUEST_TIMEOUT)
        429 -> ApiResult.Error(ApiError.Remote.TOO_MANY_REQUEST)
        in 500..599 -> ApiResult.Error(ApiError.Remote.SERVER)
        else -> ApiResult.Error(ApiError.Remote.UNKNOWN)
    }
}