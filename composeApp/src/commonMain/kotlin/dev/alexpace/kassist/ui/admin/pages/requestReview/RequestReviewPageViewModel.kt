package dev.alexpace.kassist.ui.admin.pages.requestReview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.data.utils.helpers.Codes.countryCodeMap
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.enums.nds.NeedLevelTypes
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.GeoapifyApiService
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RequestReviewPageViewModel(
    private val adminPendingDataRepository: AdminPendingDataRepository,
    private val userRepository: UserRepository,
    private val geoapifyApiService: GeoapifyApiService,
    private val naturalDisasterRepository: NaturalDisasterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RequestReviewPageState())
    val state = _state.asStateFlow()

    fun initialize(helpRequest: HelpRequest) {
        _state.update {
            it.copy(
                helpRequest = helpRequest,
                selectedStatus = helpRequest.status,
                selectedNeedLevel = helpRequest.needLevel,
                isAddressValid = null
            )
        }
        fetchVictim()
    }

    fun updateNeedLevel(needLevel: NeedLevelTypes) {
        _state.update { it.copy(selectedNeedLevel = needLevel) }
    }

    fun saveChangesAndAccept() {
        val helpRequest = _state.value.helpRequest ?: return
        val needLevel = _state.value.selectedNeedLevel ?: return

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                adminPendingDataRepository.acceptHelpRequest(helpRequest, needLevel)
                adminPendingDataRepository.rejectOrDeleteHelpRequest(helpRequest.id)
                modifyScore(200)
                _state.update { it.copy(isLoading = false) }
                SnackbarController.showSnackbar("Request accepted!")
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
                adminPendingDataRepository.rejectOrDeleteHelpRequest(helpRequest.id)
                modifyScore(-50)
                _state.update { it.copy(isLoading = false) }
                SnackbarController.showSnackbar("Request rejected")
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Failed to reject: ${e.message}")
                }
            }
        }
    }

    fun getAddressConfidenceScore() {
        val helpRequest = _state.value.helpRequest ?: return
        val disasterId = helpRequest.disasterId?.toString() ?: return

        _state.update { it.copy(isLoading = true, error = null, isAddressValid = null) }
        viewModelScope.launch {
            try {
                // Get country code
                val countryCode = getCountryCode(disasterId)
                if (countryCode.isEmpty()) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Country code not found for disaster ID: $disasterId",
                            isAddressValid = null
                        )
                    }
                    return@launch
                }

                // Call Geoapify API
                val isValid = geoapifyApiService.getConfidenceScoreForAddress(
                    address = helpRequest.address,
                    countryCode = countryCode
                )
                _state.update {
                    it.copy(
                        isLoading = false,
                        isAddressValid = isValid,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to validate address: ${e.message}",
                        isAddressValid = null
                    )
                }
            }
        }
    }

    private suspend fun getCountryCode(disasterId: String): String {
        val country = naturalDisasterRepository.getById(disasterId).firstOrNull()?.country
        return countryCodeMap[country?.lowercase()] ?: ""
    }

    private fun fetchVictim() {
        viewModelScope.launch {
            try {
                val victimId = _state.value.helpRequest?.victimId ?: return@launch
                val victim = userRepository.getById(victimId).firstOrNull()
                _state.update { it.copy(victim = victim) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to fetch victim data: ${e.message}")
                }
            }
        }
    }

    private fun modifyScore(nToAdd: Int) {
        viewModelScope.launch {
            try {
                val victim = _state.value.victim ?: return@launch
                val currentScore = victim.score
                val newScore = currentScore + nToAdd
                userRepository.update(victim.copy(score = newScore))
                _state.update { it.copy(victim = it.victim?.copy(score = newScore)) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to update score: ${e.message}")
                }
            }
        }
    }
}