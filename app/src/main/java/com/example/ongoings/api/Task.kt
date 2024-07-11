package com.example.ongoings.api

import android.os.Build
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
) {

    fun progress(): Float? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val doneAtDate =
                LocalDate.parse(this.done_at.split("T").first(), DateTimeFormatter.ISO_LOCAL_DATE)
            val doneAtTime = LocalDateTime.of(doneAtDate, LocalTime.MIN)
            val duration = Duration.between(LocalDateTime.now(), doneAtTime)
            val currentProgress = duration.abs().getSeconds() / 3600
            return (currentProgress / 200.0).toFloat()
        } else {
            // TODO("VERSION.SDK_INT < O")
            return null
        }
    }

    fun doneWhen(): String {
        return this.done_at.split("T").first()
    }
}
