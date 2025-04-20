package dev.alexpace.kassist.ui.victim.pages.proposalInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProposalInfoViewModel(
    private val helpProposalRepository: HelpProposalRepository,
    private val helpRequestRepository: HelpRequestRepository,
    private val proposalId: String
) : ViewModel() {

    val helpProposal: StateFlow<HelpProposal?> = helpProposalRepository.getById(proposalId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val helpRequest: StateFlow<HelpRequest?> = helpProposal
        .map { it?.helpRequestId }
        .flatMapLatest { id -> if (id != null) helpRequestRepository.getById(id) else flowOf(null) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun acceptProposal() {
        val currentProposal = helpProposal.value ?: return
        val updatedProposal = currentProposal.copy(status = RequestStatusTypes.Accepted)
        viewModelScope.launch {
            helpProposalRepository.update(updatedProposal)
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