package com.lininglink.ongoings.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val baseUrl = "https://sessions-to-do.herokuapp.com"

class SessionDoAPI {
    private val client = HttpClient()

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return runCatching {
            withContext(context = Dispatchers.IO) {

                val response: HttpResponse = client.post("$baseUrl/api/session") {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/json")
                        append(HttpHeaders.UserAgent, "Android Ktor")
                    }
                    setBody(Json.encodeToString(LoginRequest.serializer(), loginRequest))
                }

                if (response.status == HttpStatusCode.OK) {
                    Json.decodeFromString<LoginResponse>(response.body())
                } else {
                    throw Exception("Login response ${response.status.value}")
                }
            }
        }
    }

    suspend fun clearTask(taskId: Long, tokenString: String): Result<Boolean> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val response: HttpResponse = client.post("$baseUrl/api/tasks/${taskId}/clear") {
                    headers {
                        append(HttpHeaders.UserAgent, "Android Ktor")
                        append(HttpHeaders.Authorization, "Bearer $tokenString")
                    }
                }

                response.status == HttpStatusCode.NoContent
            }
        }
    }

    suspend fun getTasks(tokenString: String): Result<List<Task>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val response: HttpResponse = client.get("${baseUrl}/api/tasks") {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/json")
                        append(HttpHeaders.UserAgent, "Android Ktor")
                        append(HttpHeaders.Authorization, "Bearer $tokenString")
                    }
                }

                when (response.status) {
                    HttpStatusCode.OK -> {
                        val tasksJson = response.bodyAsText()
                        val jsonBuilder = Json { ignoreUnknownKeys = true }
                        jsonBuilder.decodeFromString<List<Task>>(tasksJson)
                    }

                    HttpStatusCode.Unauthorized -> {
                        throw SessionDoUnauthorizedException("Invalid or expired token")
                    }

                    else -> {
                        throw SessionDoHttpException(
                            response.status,
                            "Unexpected response: ${response.status}"
                        )
                    }
                }
            }
        }
    }
}

enum class LoadingState {
    Loading,
    Success,
    Failure,
    Error,
    Initial
}

class SessionDoUnauthorizedException(message: String) : Exception(message)
class SessionDoHttpException(val statusCode: HttpStatusCode, message: String) : Exception(message)
