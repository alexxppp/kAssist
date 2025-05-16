package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.classes.chat.ChatMessage
import dev.alexpace.kassist.domain.models.classes.chat.LiveChat
import kotlinx.coroutines.flow.Flow

interface LiveChatRepository {

    fun getAllById(userId: String): Flow<List<LiveChat>>
    fun getById(liveChatId: String): Flow<LiveChat?>
    suspend fun add(liveChat: LiveChat)
    suspend fun update(liveChat: LiveChat)
    suspend fun delete(liveChatId: String)
    suspend fun sendMessage(liveChatId: String, message: ChatMessage)
    suspend fun setAllMessagesToSeen(liveChatId: String)
}