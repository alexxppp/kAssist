package dev.alexpace.kassist.ui.admin.pages.usersActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersActivityViewModel(
    private val userRepository: UserRepository,
    private val helpRequestRepository: HelpRequestRepository,
    private val helpProposalRepository: HelpProposalRepository
) : ViewModel() {

    // Values
    private val currentUserId = Firebase.auth.currentUser?.uid
        ?: throw Exception("User is not authenticated")

    // State Flow
    private val _state = MutableStateFlow(UsersActivityPageState())
    val state = _state.asStateFlow()

    // Init
    init {
        viewModelScope.launch {
            fetchUser()
            fetchAllUsersByDisaster()
            fetchAllHelpProposals()
            fetchAllHelpRequestsByProposalId()
        }
    }

    // Functions
    private suspend fun fetchUser() {
        try {
            val user = userRepository.getById(currentUserId).firstOrNull() ?: return
            _state.update { it.copy(user = user) }
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
        }
    }

    private suspend fun fetchAllUsersByDisaster() {
        try {
            val currentDisasterId = _state.value.user?.naturalDisaster?.id ?: return
            val users = userRepository.getAllByDisaster(currentDisasterId).firstOrNull() ?: return
            _state.update { it.copy(users = users) }
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
        }
    }

    private suspend fun fetchAllHelpProposals() {
        try {
            val currentDisasterId = _state.value.user?.naturalDisaster?.id ?: return
            val helpProposals = helpProposalRepository.getAllByDisaster(currentDisasterId).firstOrNull() ?: return
            _state.update { it.copy(helpProposals = helpProposals) }
        } catch (e: Exception) {
            println("Error fetching help proposals: ${e.message}")
        }
    }

    private suspend fun fetchAllHelpRequestsByProposalId() {
        try {
            val helpProposals = _state.value.helpProposals
            if (helpProposals.isEmpty()) return
            helpProposals.forEach { helpProposal ->
                val helpRequest = helpRequestRepository.getById(helpProposal!!.helpRequestId).firstOrNull()
                _state.update { it.copy(helpRequests = it.helpRequests + helpRequest) }
            }
        } catch (e: Exception) {
            println("Error fetching help requests: ${e.message}")
        }
    }
}