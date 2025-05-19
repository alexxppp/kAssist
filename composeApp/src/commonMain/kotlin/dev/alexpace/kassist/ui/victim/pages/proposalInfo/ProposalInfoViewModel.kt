package dev.alexpace.kassist.ui.victim.pages.proposalInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.chat.LiveChat
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
    private val navigator: Navigator,
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
     * Handles the accept button click by updating the proposal status to Accepted,
     * creating a new live chat with both users, and adding 200 points to the supporter's score.
     */
    @OptIn(ExperimentalUuidApi::class)
    fun acceptProposal() {
        val updatedProposal = proposal.copy(status = RequestStatusTypes.Accepted)
        val currentRequest = _helpRequest.value
        val updatedRequest = currentRequest!!.copy(status = RequestStatusTypes.Accepted)

        val newChat = LiveChat(
            id = Uuid.random().toString(),
            victimId = proposal.victimId,
            supporterId = proposal.supporterId,
            naturalDisaster = _user.value?.naturalDisaster ?: return,
            helpRequest = currentRequest,
            helpProposal = proposal,
            isActive = true,
            messages = emptyList(),
        )

        viewModelScope.launch {
            try {
                helpProposalRepository.update(updatedProposal)
                helpRequestRepository.update(updatedRequest)
                liveChatRepository.add(newChat)
                modifyScore(200)
                SnackbarController.showSnackbar("Proposal accepted!")
            } catch (e: Exception) {
                SnackbarController.showSnackbar("Failed to accept proposal: ${e.message}")
            } finally {
                navigator.pop()
            }
        }
    }

    /**
     * Handles the decline button click by updating the proposal status to Declined
     * and subtracting 50 points from the supporter's score.
     */
    fun declineProposal() {
        val updatedProposal = proposal.copy(status = RequestStatusTypes.Declined)
        viewModelScope.launch {
            try {
                helpProposalRepository.update(updatedProposal)
                modifyScore(-50)
                SnackbarController.showSnackbar("Proposal declined")
            } catch (e: Exception) {
                SnackbarController.showSnackbar("Failed to decline proposal: ${e.message}")
            } finally {
                navigator.pop()
            }
        }
    }

    /**
     * Modifies the supporter's score by the specified amount and updates the repository.
     */
    private fun modifyScore(amount: Int) {
        viewModelScope.launch {
            try {
                val supporter = _supporter.value ?: return@launch
                val currentScore = supporter.score
                val newScore = currentScore + amount
                userRepository.update(supporter.copy(score = newScore))
                _supporter.value = supporter.copy(score = newScore)
            } catch (e: Exception) {
                SnackbarController.showSnackbar("Failed to update score: ${e.message}")
            }
        }
    }
}