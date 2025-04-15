package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class SupporterHelpPageViewModel(
    val helpRequestRepository: HelpRequestRepositoryImpl,
    val helpProposalRepository: HelpProposalRepositoryImpl,
    val userId: String
) : ViewModel() {
    private val _selectedHelpRequest = MutableStateFlow<HelpRequest?>(null)
    val selectedHelpRequest: StateFlow<HelpRequest?> = _selectedHelpRequest.asStateFlow()

    fun selectHelpRequest(request: HelpRequest?) {
        _selectedHelpRequest.value = request
    }

    fun submitHelpProposal(content: String, helpRequest: HelpRequest) {
        viewModelScope.launch {
            val newId = Random.nextInt(999999999).toString()
            val helpProposal = HelpProposal(
                id = newId,
                supporterId = userId,
                helpRequestId = helpRequest.id,
                victimId = helpRequest.victimId,
                content = content,
                status = RequestStatusTypes.Pending
            )
            helpProposalRepository.add(helpProposal)
            selectHelpRequest(null) // Close dialog after submission
        }
    }
}