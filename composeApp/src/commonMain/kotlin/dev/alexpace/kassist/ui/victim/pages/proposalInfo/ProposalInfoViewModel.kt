package dev.alexpace.kassist.ui.victim.pages.proposalInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProposalInfoViewModel(
    private val helpProposalRepository: HelpProposalRepository,
    private val helpRequestRepository: HelpRequestRepository,
    private val userRepository: UserRepository,
    private val liveChatRepository: LiveChatRepository,
    private val proposal: HelpProposal
) : ViewModel() {

    // Values
    private val currentUserId =
        Firebase.auth.currentUser?.uid ?: throw Exception("User not authenticated")

    // State Flows
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _helpRequest = MutableStateFlow<HelpRequest?>(null)
    val helpRequest = _helpRequest.asStateFlow()

    private val _supporter = MutableStateFlow<User?>(null)
    val supporter = _supporter.asStateFlow()

    // Init
    init {
        fetchUser()
        fetchData()
    }

    // Functions

    /**
     * Fetches user from UserRepository
     */
    private fun fetchUser() {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getById(currentUserId).firstOrNull()
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }

    /**
     * Fetches all necessary data once based on the initial proposalId
     */
    private fun fetchData() {
        viewModelScope.launch {
            try {
                val helpRequest =
                    helpRequestRepository.getById(proposal.helpRequestId).firstOrNull()
                _helpRequest.value = helpRequest
                val supporter = userRepository.getById(proposal.supporterId).firstOrNull()
                _supporter.value = supporter
            } catch (e: Exception) {
                _helpRequest.value = null
                _supporter.value = null
            }
        }
    }

    /**
     * Handles the accept button click by updating the proposal status to Accepted
     * and creating a new live chat with both users.
     */
    @OptIn(ExperimentalUuidApi::class)
    fun acceptProposal() {
        val updatedProposal = proposal.copy(status = RequestStatusTypes.Accepted)
        val currentRequest = _helpRequest.value ?: return
        val updatedRequest = currentRequest.copy(status = RequestStatusTypes.Accepted)

        val newChat = LiveChat(
            id = Uuid.random().toString(),
            victimId = proposal.victimId,
            supporterId = proposal.supporterId,
            naturalDisaster = _user.value!!.naturalDisaster!!,
            helpRequest = currentRequest,
            helpProposal = proposal,
            isActive = true,
            messages = emptyList(),
        )

        viewModelScope.launch {
            helpProposalRepository.update(updatedProposal)
            helpRequestRepository.update(updatedRequest)
            liveChatRepository.add(newChat)
        }

        SnackbarController.showSnackbar("Proposal accepted!")
    }

    /**
     * Handles the decline button click by updating the proposal status to Declined
     */
    fun declineProposal() {
        val updatedProposal = proposal.copy(status = RequestStatusTypes.Declined)
        viewModelScope.launch {
            helpProposalRepository.update(updatedProposal)
        }
        SnackbarController.showSnackbar("Proposal declined")
    }
}