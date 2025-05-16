package dev.alexpace.kassist.ui.supporter.pages.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.classes.chat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SupporterContactPageViewModel(
    private val liveChatRepository: LiveChatRepository,
    private val userRepository: UserRepository,
    private val supporterId: String
) : ViewModel() {

    // State Flows
    private val _liveChats = MutableStateFlow<List<LiveChat>>(emptyList())
    val liveChats = _liveChats.asStateFlow()

    private val _userNames = MutableStateFlow<Map<String, String>>(emptyMap())
    val userNames = _userNames.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // Init
    init {
        fetchLiveChats()
    }

    // Functions

    /**
     * Fetches all chats of the current supporter from the LiveChatRepository
     */
    private fun fetchLiveChats() {
        _isLoading.value = true
        viewModelScope.launch {
            liveChatRepository.getAllById(supporterId).collect { chats ->
                _liveChats.value = chats
                updateUserNames(chats)
            }
        }
        _isLoading.value = false
    }

    /**
     * Updates the user names map with the names of the users in the live chats,
     * replacing the IDs
     */
    private fun updateUserNames(chats: List<LiveChat>) {
        _isLoading.value = true
        val receiverIds =
            chats.map { if (it.victimId == supporterId) it.supporterId else it.victimId }.toSet()
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
        _isLoading.value = false
    }
}