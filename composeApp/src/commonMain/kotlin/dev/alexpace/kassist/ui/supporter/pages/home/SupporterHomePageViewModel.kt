package dev.alexpace.kassist.ui.supporter.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupporterHomePageViewModel(
    private val helpProposalRepository: HelpProposalRepository
) : ViewModel() {

    // Values
    private val userId = Firebase.auth.currentUser?.uid
        ?: throw Exception("No authenticated user found") // TODO: Handle null user more nicely

    // State Flows
    private val _acceptedHelpProposals = MutableStateFlow<List<HelpProposal>>(emptyList())
    val acceptedHelpProposals: StateFlow<List<HelpProposal>> = _acceptedHelpProposals

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Init
    init {
        fetchAcceptedHelpProposals()
    }

    // Functions

    /**
     * Fetches all accepted help proposals from the HelpProposalRepository
     */
    private fun fetchAcceptedHelpProposals() {
        _isLoading.value = true
        viewModelScope.launch {
            helpProposalRepository.getBySupporterIdAndStatuses(
                userId, listOf(RequestStatusTypes.Accepted)
            ).collect { acceptedProposals ->
                _acceptedHelpProposals.value = acceptedProposals
            }
        }
        _isLoading.value = false
    }
}