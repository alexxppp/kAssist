package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SupporterHelpPageViewModel(
    private val helpRequestRepository: HelpRequestRepository,
    private val helpProposalRepository: HelpProposalRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Values
    private val currentUserId = Firebase.auth.currentUser?.uid
    // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    // State Flows
    private val _selectedHelpRequest = MutableStateFlow<HelpRequest?>(null)
    val selectedHelpRequest = _selectedHelpRequest.asStateFlow()

    private val _helpRequests = MutableStateFlow<List<HelpRequest>>(emptyList())
    val helpRequests = _helpRequests.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // Init
    init {
        fetchUser()
    }

    // Functions

    fun selectHelpRequest(request: HelpRequest?) {
        _selectedHelpRequest.value = request
    }

    /**
     * Submits help proposal by calling the HelpProposalRepository
     * and sets the selected help request to null
     */
    fun submitHelpProposal(content: String, helpRequest: HelpRequest) {
        viewModelScope.launch {
            try {
                helpProposalRepository.add(buildHelpProposal(content, helpRequest))
                selectHelpRequest(null)
            } catch (e: Exception) {
                println("Error submitting proposal: ${e.message}")
            }
        }
    }

    // TODO: Implement
    private fun checkForm() {

    }

    /**
     * Fetches user and triggers fetchHelpRequests if user and disaster are available
     */
    private fun fetchUser() {
        viewModelScope.launch {
            try {
                val currentUser = userRepository.getById(currentUserId).firstOrNull()
                _user.value = currentUser

                val disasterId = currentUser?.naturalDisaster?.id
                fetchHelpRequests(disasterId!!)
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
            }
        }
    }

    /**
     * Fetches all help requests by disasterId and keeps them updated
     * with the current db state
     */
    private fun fetchHelpRequests(disasterId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                helpRequestRepository.getAllByDisaster(disasterId)
                    .collectLatest { helpRequests ->
                        _helpRequests.value = helpRequests
                    }
            } catch (e: Exception) {
                println("Error fetching help requests: ${e.message}")
                _helpRequests.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
        _isLoading.value = false
    }

    /**
     * Builds a help proposal from the given content and help request
     * Uuid is experimental
     */
    @OptIn(ExperimentalUuidApi::class)
    private fun buildHelpProposal(content: String, helpRequest: HelpRequest): HelpProposal {
        return HelpProposal(
            id = Uuid.random().toString(),
            supporterId = currentUserId,
            helpRequestId = helpRequest.id,
            victimId = helpRequest.victimId,
            content = content,
            status = RequestStatusTypes.Pending,
            supporterName = user.value!!.name,
            victimName = helpRequest.victimName
        )
    }
}