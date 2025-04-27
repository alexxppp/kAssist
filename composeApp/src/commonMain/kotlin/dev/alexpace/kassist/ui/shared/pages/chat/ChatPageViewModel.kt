package dev.alexpace.kassist.ui.shared.pages.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.shared.liveChat.ChatMessage
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ChatPageViewModel(
    private val liveChatRepository: LiveChatRepository
) : ViewModel() {

    private val _liveChat = MutableStateFlow<LiveChat?>(null)
    val liveChat: StateFlow<LiveChat?> = _liveChat.asStateFlow()

    fun loadChat(liveChatId: String) {
        viewModelScope.launch {
            liveChatRepository.getById(liveChatId).collect { chat ->
                _liveChat.value = chat
                setAllMessagesToSeen(liveChatId)
            }
        }
    }

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

    private fun setAllMessagesToSeen(liveChatId: String) {
        viewModelScope.launch {
            liveChatRepository.setAllMessagesToSeen(liveChatId)
        }
    }
}