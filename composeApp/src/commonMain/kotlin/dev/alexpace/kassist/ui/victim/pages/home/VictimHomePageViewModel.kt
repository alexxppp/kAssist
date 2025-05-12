package dev.alexpace.kassist.ui.victim.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VictimHomePageViewModel(
    private val helpProposalRepository: HelpProposalRepository
) : ViewModel() {

    // Values
    private val currentUserId = Firebase.auth.currentUser?.uid
        // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    // State Flows
    private val _helpProposals = MutableStateFlow<List<HelpProposal?>>(emptyList())
    val helpProposals = _helpProposals.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // Init
    init {
        fetchHelpProposals()
    }

    // Functions

    /**
     * Fetches Help Proposals from HelpProposalRepository
     */
    private fun fetchHelpProposals() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                helpProposalRepository.getByVictimIdAndStatuses(
                    currentUserId,
                    listOf(RequestStatusTypes.Pending, RequestStatusTypes.Accepted)
                ).collect { proposals ->
                    _helpProposals.value = proposals
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}