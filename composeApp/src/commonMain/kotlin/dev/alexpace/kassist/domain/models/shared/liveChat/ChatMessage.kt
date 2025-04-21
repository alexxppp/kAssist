package dev.alexpace.kassist.domain.models.shared.liveChat

data class ChatMessage(
    val senderId: String,
    val content: String,
    val timestamp: Long
)
