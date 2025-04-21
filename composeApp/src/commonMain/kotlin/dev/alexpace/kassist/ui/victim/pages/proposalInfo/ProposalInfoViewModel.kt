package dev.alexpace.kassist.ui.victim.pages.proposalInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProposalInfoViewModel(
    private val helpProposalRepository: HelpProposalRepository,
    private val helpRequestRepository: HelpRequestRepository,
    initialProposalId: String
) : ViewModel() {

    private val proposalId = MutableStateFlow(initialProposalId)

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

    fun acceptProposal() {
        val currentProposal = helpProposal.value ?: return
        val updatedProposal = currentProposal.copy(status = RequestStatusTypes.Accepted)
        val currentRequest = helpRequest.value ?: return
        val updatedRequest = currentRequest.copy(status = RequestStatusTypes.Accepted)
        viewModelScope.launch {
            helpProposalRepository.update(updatedProposal)
            helpRequestRepository.update(updatedRequest)
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