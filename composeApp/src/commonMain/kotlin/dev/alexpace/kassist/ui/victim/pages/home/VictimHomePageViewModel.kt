package dev.alexpace.kassist.ui.victim.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class VictimHomePageViewModel(
    helpProposalRepository: HelpProposalRepository,
    helpRequestRepositoryImpl: HelpRequestRepository,
    currentUserId: String
) : ViewModel() {

    private val placeholderHelpRequest = HelpRequest(
        id = "",
        victimId = "",
        victimName = "Unknown",
        address = "Unknown",
        description = "No request data available",
        needLevel = NeedLevelTypes.Low,
        status = RequestStatusTypes.Pending
    )

    val proposalsWithRequests: StateFlow<List<Pair<HelpProposal, HelpRequest>>> =
        helpProposalRepository.getHelpProposals().map { proposals ->
            proposals.map { proposal -> proposal to placeholderHelpRequest }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}