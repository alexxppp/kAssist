package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class SupporterHelpPageViewModel(
    private val helpRequestRepository: HelpRequestRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Values
    private val currentUserId = Firebase.auth.currentUser?.uid
        ?: throw Exception("User not authenticated")

    // State Flows
    private val _helpRequests = MutableStateFlow<List<HelpRequest>>(emptyList())
    val helpRequests = _helpRequests.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // Init
    init {
        fetchUser()
    }

    // Functions

    /**
     * Fetches user and triggers fetchHelpRequests if user and disaster are available
     */
    private fun fetchUser() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val currentUser = userRepository.getById(currentUserId).firstOrNull()
                _user.value = currentUser

                val disasterId = currentUser?.naturalDisaster?.id
                fetchHelpRequests(disasterId!!)
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
            }
        }
        _isLoading.value = false
    }

    /**
     * Fetches all help requests by disasterId and keeps them updated
     * with the current db state
     */
    private fun fetchHelpRequests(disasterId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                helpRequestRepository.getAllByDisaster(disasterId, currentUserId)
                    .collectLatest { helpRequests ->
                        _helpRequests.value = helpRequests
                    }
            } catch (e: Exception) {
                println("Error fetching help requests: ${e.message}")
                _helpRequests.value = emptyList()
            }
        }
        _isLoading.value = false
    }
}