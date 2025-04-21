package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import kotlinx.coroutines.flow.Flow

interface LiveChatRepository {

    fun getAllById(userId: String): Flow<List<LiveChat>>
    suspend fun add(liveChat: LiveChat)
    suspend fun update(liveChat: LiveChat)
    suspend fun delete(liveChatId: String)

}