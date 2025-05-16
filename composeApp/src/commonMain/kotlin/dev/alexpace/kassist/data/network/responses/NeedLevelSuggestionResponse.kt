package dev.alexpace.kassist.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class NeedLevelSuggestionResponse (
    val suggestedNeedLevel: String,
)