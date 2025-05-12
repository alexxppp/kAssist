package dev.alexpace.kassist.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class LiveNewsResponse(
    val disaster: String,
    val summary: String
)