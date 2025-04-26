package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.shared.liveChat.ChatMessage
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class LiveChatRepositoryImpl : LiveChatRepository {

    private val firestore = Firebase.firestore
    private val liveChatCollection = firestore.collection("LiveChat")

    override fun getAllById(userId: String) = flow {
        val victimChatsFlow = liveChatCollection
            .where { "victimId" equalTo userId }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<LiveChat>()
                }
            }

        val supporterChatsFlow = liveChatCollection
            .where { "supporterId" equalTo userId }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<LiveChat>()
                }
            }

        merge(victimChatsFlow, supporterChatsFlow)
            .collect { liveChats ->
                emit(liveChats.distinctBy { it.id })
            }
    }

    override fun getById(liveChatId: String) = flow {
        liveChatCollection
            .document(liveChatId)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<LiveChat>())
            }
    }

    override suspend fun add(liveChat: LiveChat) {
        liveChatCollection
            .document(liveChat.id)
            .set(liveChat.copy(id = liveChat.id))
    }

    override suspend fun update(liveChat: LiveChat) {
        liveChatCollection
            .document(liveChat.id)
            .set(liveChat)
    }

    override suspend fun delete(liveChatId: String) {
        liveChatCollection
            .document(liveChatId)
            .delete()
    }

    override suspend fun sendMessage(liveChatId: String, message: ChatMessage) {
        liveChatCollection
            .document(liveChatId)
            .update("messages" to FieldValue.arrayUnion(message))
    }

}