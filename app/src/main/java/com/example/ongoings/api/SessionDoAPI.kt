package com.example.ongoings.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

private const val baseUrl = "https://sessions-to-do.herokuapp.com"

class SessionDoAPI {
    companion object {
        val shared = SessionDoAPI()
    }

    private val client = HttpClient()

    suspend fun login(): Result<String> {
        return runCatching {
            val response: HttpResponse = client.post("$baseUrl/api/session")
            // client.close()
            // commented out to avoid JobCancellationException
            response.body()
        }
    }

}