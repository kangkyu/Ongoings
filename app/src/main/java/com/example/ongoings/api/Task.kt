package com.example.ongoings.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("name")
    val name: String,
    @SerialName("comment")
    val comment: String,
    @SerialName("done_at")
    val done_at: String,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("updated_at")
    val updated_at: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("is_daily")
    val is_daily: Boolean
)
