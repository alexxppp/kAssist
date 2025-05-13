package dev.alexpace.kassist.ui.admin.pages.requestReview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RequestReviewPageViewModel(
    private val adminPendingDataRepository: AdminPendingDataRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RequestReviewPageState())
    val state = _state.asStateFlow()

    fun initialize(helpRequest: HelpRequest) {
        _state.update {
            it.copy(
                helpRequest = helpRequest,
                selectedStatus = helpRequest.status,
                selectedNeedLevel = helpRequest.needLevel
            )
        }
    }

    fun updateStatus(status: RequestStatusTypes) {
        _state.update { it.copy(selectedStatus = status) }
    }

    fun updateNeedLevel(needLevel: NeedLevelTypes) {
        _state.update { it.copy(selectedNeedLevel = needLevel) }
    }

    fun saveChanges() {
        val helpRequest = _state.value.helpRequest ?: return
        val status = _state.value.selectedStatus ?: return
        val needLevel = _state.value.selectedNeedLevel ?: return

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                adminPendingDataRepository.acceptHelpRequest(helpRequest, needLevel)
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Failed to save: ${e.message}")
                }
            }
        }
    }

    fun rejectRequest() {
        val helpRequest = _state.value.helpRequest ?: return

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                adminPendingDataRepository.rejectHelpRequest(helpRequest.id)
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Failed to reject: ${e.message}")
                }
            }
        }
    }
}