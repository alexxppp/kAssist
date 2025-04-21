package dev.alexpace.kassist.ui.supporter.pages.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupporterContactPageViewModel(
    private val liveChatRepository: LiveChatRepository,
    private val supporterId: String
) : ViewModel() {
    private val _liveChats = MutableStateFlow<List<LiveChat>>(emptyList())
    val liveChats: StateFlow<List<LiveChat>> = _liveChats

    init {
        viewModelScope.launch {
            liveChatRepository.getAllById(supporterId).collect { chats ->
                _liveChats.value = chats.filter { it.supporterId == supporterId || it.victimId == supporterId }
            }
        }
    }
}