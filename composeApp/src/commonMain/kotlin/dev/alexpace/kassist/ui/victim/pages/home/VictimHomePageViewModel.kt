package dev.alexpace.kassist.ui.victim.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class VictimHomePageViewModel(
    helpProposalRepository: HelpProposalRepository,
    helpRequestRepositoryImpl: HelpRequestRepository,
    currentUserId: String
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val helpProposals: StateFlow<List<HelpProposal?>> =
        helpProposalRepository.getByVictimId(currentUserId)
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                initialValue = emptyList()
            )
            .also { _isLoading.value = false }

}