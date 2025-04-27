package dev.alexpace.kassist.domain.models.shared.liveChat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val seen: Boolean = false
)
