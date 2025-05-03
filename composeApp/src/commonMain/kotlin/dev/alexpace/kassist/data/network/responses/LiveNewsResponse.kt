package dev.alexpace.kassist.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class LiveNewsSummary(
    val title: String,
    val summary: String
)

@Serializable
data class LiveNewsResponse(
    val disaster: String,
    val summaries: List<LiveNewsSummary>
)