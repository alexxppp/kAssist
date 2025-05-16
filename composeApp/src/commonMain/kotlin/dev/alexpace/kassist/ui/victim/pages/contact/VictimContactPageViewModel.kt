package dev.alexpace.kassist.ui.victim.pages.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.classes.chat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VictimContactPageViewModel(
    private val liveChatRepository: LiveChatRepository,
    private val userRepository: UserRepository,
    private val victimId: String
) : ViewModel() {

    // State Flows
    private val _liveChats = MutableStateFlow<List<LiveChat>>(emptyList())
    val liveChats = _liveChats.asStateFlow()

    private val _userNames = MutableStateFlow<Map<String, String>>(emptyMap())
    val userNames = _userNames.asStateFlow()

    // Init
    init {
        fetchAllChats()
    }

    // Functions

    /**
     * Fetches all user chats from LiveChatRepository
     */
    private fun fetchAllChats() {
        viewModelScope.launch {
            liveChatRepository.getAllById(victimId).collect { chats ->
                _liveChats.value = chats
                updateUserNames(chats)
            }
        }
    }

    /**
     * Updates the user names map based on the live chats, changing the displayed
     * IDs to the name of the receiver
     */
    private fun updateUserNames(chats: List<LiveChat>) {
        val receiverIds = chats.map { if (it.victimId == victimId) it.supporterId else it.victimId }.toSet()
        val currentNames = _userNames.value
        for (id in receiverIds) {
            if (!currentNames.containsKey(id)) {
                viewModelScope.launch {
                    userRepository.getById(id).collect { user ->
                        user?.let {
                            _userNames.value += (id to it.name)
                        }
                    }
                }
            }
        }
    }
}