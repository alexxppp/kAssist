package dev.alexpace.kassist.ui.supporter.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SupporterHomePageViewModel(
    private val helpProposalRepository: HelpProposalRepository
) : ViewModel() {

    private val userId = Firebase.auth.currentUser?.uid
        ?: throw Exception("No authenticated user found")

    val acceptedHelpProposals: StateFlow<List<HelpProposal>> =
        helpProposalRepository.getAcceptedBySupporterId(userId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}