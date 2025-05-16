package dev.alexpace.kassist.ui.victim.pages.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.classes.help.HelpItem
import dev.alexpace.kassist.domain.models.enums.app.UnitType
import dev.alexpace.kassist.domain.models.enums.help.HelpItemType
import dev.alexpace.kassist.domain.models.enums.nds.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class VictimHelpPageViewModel(
    private val helpRequestRepository: HelpRequestRepository,
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

    private val _helpItems = MutableStateFlow<List<HelpItem>>(emptyList())
    val helpItems = _helpItems.asStateFlow()

    // Init
    init {
        fetchUser()
    }

    // Functions

    private fun fetchUser() {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getById(currentUserId).firstOrNull()
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }

    fun updateAddress(newAddress: String) {
        _address.value = newAddress
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun addHelpItem() {
        if (_helpItems.value.size < 5) {
            _helpItems.value += HelpItem(
                id = Uuid.random().toString(),
                name = "",
                details = null,
                neededQuantity = 0,
                unit = UnitType.Piece,
                type = HelpItemType.Other
            )
        }
    }

    fun updateHelpItem(index: Int, updatedItem: HelpItem) {
        _helpItems.value = _helpItems.value.toMutableList().apply { set(index, updatedItem) }
    }

    fun removeHelpItem(index: Int) {
        _helpItems.value = _helpItems.value.toMutableList().apply { removeAt(index) }
    }

    fun submitHelpRequest() {
        if (checkForm()) {
            val newHelpRequest = HelpRequest(
                id = Uuid.random().toString(),
                victimId = user.value!!.id,
                victimName = user.value!!.name,
                disasterId = user.value!!.naturalDisaster?.id,
                address = address.value,
                description = description.value,
                items = _helpItems.value,
                needLevel = NeedLevelTypes.NotAssigned,
                status = RequestStatusTypes.NotAssigned
            )
            viewModelScope.launch {
                helpRequestRepository.addPending(newHelpRequest)
                clearForm()
                SnackbarController.showSnackbar("Help request submitted!")
            }
        }
    }

    private fun checkForm(): Boolean {
        if (address.value.isEmpty() || description.value.length < 5) {
            SnackbarController.showSnackbar("Address is required and description must be at least 5 characters.")
            return false
        }
        return true
    }

    private fun clearForm() {
        _address.value = ""
        _description.value = ""
        _helpItems.value = emptyList()
    }
}