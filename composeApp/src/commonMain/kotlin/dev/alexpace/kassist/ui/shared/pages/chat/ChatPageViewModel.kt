package dev.alexpace.kassist.ui.shared.pages.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.shared.liveChat.ChatMessage
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ChatPageViewModel(
    private val liveChatRepository: LiveChatRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // State Flows
    private val _liveChat = MutableStateFlow<LiveChat?>(null)
    val liveChat: StateFlow<LiveChat?> = _liveChat.asStateFlow()

    private val _receiverName = MutableStateFlow<String?>(null)
    val receiverName: StateFlow<String?> = _receiverName.asStateFlow()

    // Functions

    /**
     * Loads chat from repository and sets receiver name
     */
    fun loadChat(liveChatId: String) {
        viewModelScope.launch {
            liveChatRepository.getById(liveChatId).collect { chat ->
                _liveChat.value = chat
                if (chat != null) {
                    val receiverId = getReceiverId(chat)
                    // Set receiver name (the other user in the chat)
                    if (receiverId != null) {
                        viewModelScope.launch {
                            userRepository.getById(receiverId).collect { user ->
                                _receiverName.value = user?.name
                            }
                        }
                    }
                }
                setAllMessagesToSeen(liveChatId) // TODO: Fix
            }
        }
    }

    /**
     * Gets the ID of the receiver based on the current user's ID
     */
    private fun getReceiverId(liveChat: LiveChat): String? {
        val currentUserId = Firebase.auth.currentUser?.uid ?: return null
        return when (currentUserId) {
            liveChat.victimId -> liveChat.supporterId
            liveChat.supporterId -> liveChat.victimId
            else -> null
        }
    }

    /**
     * Creates and sends a message
     */
    fun sendMessage(liveChatId: String, content: String) {
        val currentUserId = Firebase.auth.currentUser?.uid ?: return
        val message = ChatMessage(
            senderId = currentUserId,
            content = content,
            timestamp = Clock.System.now().toEpochMilliseconds(),
            seen = false
        )
        viewModelScope.launch {
            liveChatRepository.sendMessage(liveChatId, message)
        }
    }

    /**
     * TODO: Fix
     */
    private fun setAllMessagesToSeen(liveChatId: String) {
        viewModelScope.launch {
            liveChatRepository.setAllMessagesToSeen(liveChatId)
        }
    }
}