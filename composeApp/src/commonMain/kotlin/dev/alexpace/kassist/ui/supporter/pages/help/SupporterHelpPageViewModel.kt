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
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SupporterHelpPageViewModel(
    val helpRequestRepository: HelpRequestRepositoryImpl,
    private val helpProposalRepository: HelpProposalRepositoryImpl,
    private val userId: String
) : ViewModel() {

    private val _selectedHelpRequest = MutableStateFlow<HelpRequest?>(null)
    val selectedHelpRequest: StateFlow<HelpRequest?> = _selectedHelpRequest.asStateFlow()

    fun selectHelpRequest(request: HelpRequest?) {
        _selectedHelpRequest.value = request
    }

    fun submitHelpProposal(content: String, helpRequest: HelpRequest) {
        viewModelScope.launch {
            try {
                helpProposalRepository.add(buildHelpProposal(content, helpRequest))

                selectHelpRequest(null)
            } catch (e: Exception) {
                // Log error (replace with proper logging in production)
                println("Error submitting proposal: ${e.message}")
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildHelpProposal(content: String, helpRequest: HelpRequest): HelpProposal {
        return HelpProposal(
            id = Uuid.random().toString(),
            supporterId = userId,
            helpRequestId = helpRequest.id,
            victimId = helpRequest.victimId,
            content = content,
            status = RequestStatusTypes.Pending
        )
    }
}