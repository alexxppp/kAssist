package dev.alexpace.kassist.domain.models.classes.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val seen: Boolean = false
)
