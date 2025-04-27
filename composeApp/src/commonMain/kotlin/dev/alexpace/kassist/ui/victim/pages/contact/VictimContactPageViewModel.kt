package dev.alexpace.kassist.ui.victim.pages.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VictimContactPageViewModel(
    private val liveChatRepository: LiveChatRepository,
    private val victimId: String
) : ViewModel() {
    private val _liveChats = MutableStateFlow<List<LiveChat>>(emptyList())
    val liveChats = _liveChats.asStateFlow()

    init {
        viewModelScope.launch {
            liveChatRepository.getAllById(victimId).collect { chats ->
                _liveChats.value = chats
            }
        }
    }
}