package dev.alexpace.kassist.ui.victim.pages.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import kotlinx.coroutines.launch

class VictimHelpPageViewModel(
    private val helpRequestRepository: HelpRequestRepositoryImpl
) : ViewModel() {
    fun submitHelpRequest(helpRequest: HelpRequest) {
        viewModelScope.launch {
            helpRequestRepository.addHelpRequest(helpRequest)
        }
    }
}