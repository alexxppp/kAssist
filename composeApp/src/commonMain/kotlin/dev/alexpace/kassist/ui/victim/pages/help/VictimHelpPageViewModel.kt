package dev.alexpace.kassist.ui.victim.pages.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class VictimHelpPageViewModel(
    private val helpRequestRepository: HelpRequestRepositoryImpl,
    private val userRepository: UserRepository
) : ViewModel() {

    // Values
    private val currentUserId =
        Firebase.auth.currentUser?.uid ?: throw Exception("User not authenticated")

    // StateFlows
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    // Init
    init {
        fetchUser()
    }

    // Functions

    /**
     * Fetches user from UserRepository
     */
    private fun fetchUser() {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getById(currentUserId).firstOrNull()
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }

    /**
     * Updates address
     */
    fun updateAddress(newAddress: String) {
        _address.value = newAddress
    }

    /**
     * Updates description
     */
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    /**
     * Submits a help request by calling the HelpRequestRepository
     */
    fun submitHelpRequest(helpRequest: HelpRequest) {
        if (checkForm()) {
            viewModelScope.launch {
                helpRequestRepository.addPending(helpRequest)
                clearForm()
                SnackbarController.showSnackbar("Help request submitted!")
            }
        }
    }

    private fun checkForm(): Boolean {
        if (address.value.isEmpty() || description.value.isEmpty()) {
            SnackbarController.showSnackbar("Please fill in all fields")
            return false
        }
        return true
    }

    /**
     * Clears the form fields
     */
    private fun clearForm() {
        _address.value = ""
        _description.value = ""
    }
}