package com.example.ongoings.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("token")
    val token: String,
    @SerialName("user_id")
    val user_id: Int
)
