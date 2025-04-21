package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat

interface LiveChatRepository {

    fun getAllById(userId: String): List<LiveChat>
    fun add(liveChat: LiveChat)
    fun update(liveChat: LiveChat)
    fun delete(liveChatId: String)

}