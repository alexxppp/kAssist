package dev.alexpace.kassist.ui.supporter.pages.helpRequestInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.classes.help.HelpItem
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
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

class SupporterHelpRequestInfoPageViewModel(
    private val userRepository: UserRepository,
    private val helpProposalRepository: HelpProposalRepository,
    private val helpRequest: HelpRequest
) : ViewModel() {

    // Values
    private val currentUserId = Firebase.auth.currentUser?.uid
        ?: throw Exception("User not authenticated")

    // State Flows
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _estimatedTimeAmount = MutableStateFlow("")
    val estimatedTimeAmount = _estimatedTimeAmount.asStateFlow()

    private val _estimatedTimeUnit = MutableStateFlow("")
    val estimatedTimeUnit = _estimatedTimeUnit.asStateFlow()

    private val _selectedItemIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedItemIds = _selectedItemIds.asStateFlow()

    // Init
    init {
        fetchUser()
    }

    // Functions

    /**
     * Fetches user data
     */
    private fun fetchUser() {
        viewModelScope.launch {
            try {
                val currentUser = userRepository.getById(currentUserId).firstOrNull()
                _user.value = currentUser
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
            }
        }
    }

    /**
     * Updates the description state
     */
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    /**
     * Updates the estimated time amount state
     */
    fun updateEstimatedTimeAmount(newAmount: String) {
        _estimatedTimeAmount.value = newAmount
    }

    /**
     * Updates the estimated time unit state
     */
    fun updateEstimatedTimeUnit(newUnit: String) {
        _estimatedTimeUnit.value = newUnit
    }

    /**
     * Toggles the selection of a help item
     */
    fun toggleItemSelection(itemId: String) {
        val current = _selectedItemIds.value
        _selectedItemIds.value = if (current.contains(itemId)) {
            current - itemId
        } else {
            current + itemId
        }
    }

    /**
     * Submits the help proposal using internal states
     */
    fun submitHelpProposal() {
        viewModelScope.launch {
            try {
                val content = _description.value
                val timeAmount = _estimatedTimeAmount.value
                val timeUnit = _estimatedTimeUnit.value
                val error = checkForm(content, timeAmount, timeUnit)
                if (error != null) {
                    println(error)
                    return@launch
                }
                val estimatedTime = if (timeAmount.isNotBlank() && timeUnit.isNotBlank()) {
                    "$timeAmount $timeUnit"
                } else {
                    null
                }
                val selectedItems = helpRequest.items.filter { it != null && _selectedItemIds.value.contains(it.id) }
                val proposal = buildHelpProposal(content, estimatedTime, selectedItems)
                helpProposalRepository.add(proposal)
            } catch (e: Exception) {
                println("Error submitting proposal: ${e.message}")
            } finally {
                clearForm()
                SnackbarController.showSnackbar("Help proposal submitted")
            }
        }
    }

    /**
     * Clears the form by resetting all input states
     */
    private fun clearForm() {
        _description.value = ""
        _estimatedTimeAmount.value = ""
        _estimatedTimeUnit.value = ""
        _selectedItemIds.value = emptySet()
    }

    /**
     * Validates the form inputs
     */
    fun checkForm(content: String, timeAmount: String, timeUnit: String): String? {
        if (content.isBlank()) {
            return "Please describe how you can help."
        }
        if (timeAmount.isNotBlank()) {
            if (timeAmount.toIntOrNull() == null || timeAmount.toInt() < 1) {
                return "Please enter a valid time amount (1 or more)."
            }
            if (timeUnit.isBlank()) {
                return "Please select a time unit."
            }
        }
        return null
    }

    /**
     * Builds a help proposal with selected items
     */
    @OptIn(ExperimentalUuidApi::class)
    private fun buildHelpProposal(
        content: String,
        estimatedTime: String?,
        selectedItems: List<HelpItem?>
    ): HelpProposal {
        return HelpProposal(
            id = Uuid.random().toString(),
            supporterId = currentUserId,
            helpRequestId = helpRequest.id,
            victimId = helpRequest.victimId,
            content = content,
            status = RequestStatusTypes.Pending,
            supporterName = user.value!!.name,
            victimName = helpRequest.victimName,
            requiredTime = estimatedTime ?: "Not provided",
            fulfilledItems = selectedItems.ifEmpty { null }
        )
    }
}