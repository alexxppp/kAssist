package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.classes.chat.ChatMessage
import dev.alexpace.kassist.domain.models.classes.chat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.combine
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

        // Combine the flows to emit a single list of distinct chats
        combine(victimChatsFlow, supporterChatsFlow) { victimChats, supporterChats ->
            (victimChats + supporterChats).distinctBy { it.id }
        }.collect { liveChats ->
            emit(liveChats)
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
        // Convert ChatMessage to a Map for Firestore compatibility
        val messageMap = mapOf(
            "senderId" to message.senderId,
            "content" to message.content.trim(),
            "timestamp" to message.timestamp
        )
        liveChatCollection
            .document(liveChatId)
            .update("messages" to FieldValue.arrayUnion(messageMap))
    }

    override suspend fun setAllMessagesToSeen(liveChatId: String) {
        val documentSnapshot = liveChatCollection
            .document(liveChatId)
            .get()

        val liveChat = documentSnapshot.data<LiveChat>()
        val updatedMessages = liveChat.messages.map { message ->
            message.copy(seen = true)
        }

        liveChatCollection
            .document(liveChatId)
            .update("messages" to updatedMessages)
    }
}