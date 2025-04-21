package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository

class LiveChatRepositoryImpl: LiveChatRepository {
    override fun getAllById(userId: String): List<LiveChat> {

    }

    override fun add(liveChat: LiveChat) {
        TODO("Not yet implemented")
    }

    override fun update(liveChat: LiveChat) {
        TODO("Not yet implemented")
    }

    override fun delete(liveChatId: String) {
        TODO("Not yet implemented")
    }

}