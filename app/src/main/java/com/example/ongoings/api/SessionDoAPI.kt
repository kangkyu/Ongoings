package com.example.ongoings.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

private const val baseUrl = "https://sessions-to-do.herokuapp.com"

class SessionDoAPI {
    companion object {
        val shared = SessionDoAPI()
    }

    private val client = HttpClient()

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return runCatching {
            val response: HttpResponse = client.post("$baseUrl/api/session") {
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.UserAgent, "Android Ktor")
                }
                setBody(Json.encodeToString(LoginRequest.serializer(), loginRequest))
            }

            // client.close()
            // commented out to avoid JobCancellationException

            if (response.status == HttpStatusCode.OK) {
                Json.decodeFromString<LoginResponse>(response.body())
            } else {
                throw Exception("Login response ${response.status.value}")
            }
        }
    }
}
