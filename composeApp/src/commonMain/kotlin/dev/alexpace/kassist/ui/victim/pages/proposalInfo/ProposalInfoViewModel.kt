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
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProposalInfoViewModel(
    private val helpProposalRepository: HelpProposalRepository,
    private val helpRequestRepository: HelpRequestRepository,
    private val userRepository: UserRepository,
    private val liveChatRepository: LiveChatRepository,
    initialProposalId: String
) : ViewModel() {

    private val proposalId = MutableStateFlow(initialProposalId)

    private val userId = Firebase.auth.currentUser?.uid

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        userId?.let { uid ->
            viewModelScope.launch {
                try {
                    _user.value = userRepository.getById(uid).firstOrNull()
                } catch (e: Exception) {
                    println(userId)
                    println("Error fetching user: ${e.message}")
                    _user.value = null
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val helpProposal: StateFlow<HelpProposal?> = proposalId
        .flatMapLatest { id -> helpProposalRepository.getById(id) }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val helpRequest: StateFlow<HelpRequest?> = helpProposal
        .flatMapLatest { proposal ->
            val helpRequestId = proposal?.helpRequestId
            if (helpRequestId != null) {
                helpRequestRepository.getById(helpRequestId)
            } else {
                flowOf(null)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    // Function to update the proposal ID when navigating to a new proposal
    fun updateProposalId(newProposalId: String) {
        proposalId.value = newProposalId
    }

    @OptIn(ExperimentalUuidApi::class)
    fun acceptProposal() {
        val currentProposal = helpProposal.value ?: return
        val updatedProposal = currentProposal.copy(status = RequestStatusTypes.Accepted)
        val currentRequest = helpRequest.value ?: return
        val updatedRequest = currentRequest.copy(status = RequestStatusTypes.Accepted)

        val newChat = LiveChat(
            id = Uuid.random().toString(),
            victimId = currentProposal.victimId,
            supporterId = currentProposal.supporterId,
            naturalDisaster = user.value!!.naturalDisaster!!,
            helpRequest = currentRequest,
            helpProposal = currentProposal,
            isActive = true,
            messages = emptyList(),
        )

        viewModelScope.launch {
            helpProposalRepository.update(updatedProposal)
            helpRequestRepository.update(updatedRequest)
            liveChatRepository.add(newChat)
        }
    }

    fun declineProposal() {
        val currentProposal = helpProposal.value ?: return
        val updatedProposal = currentProposal.copy(status = RequestStatusTypes.Declined)
        viewModelScope.launch {
            helpProposalRepository.update(updatedProposal)
        }
    }
}