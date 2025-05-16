package dev.alexpace.kassist.ui.shared.pages.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.models.enums.user.UserType
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.navigation.screens.WelcomeScreen
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsPageViewModel(
    private val userRepository: UserRepository,
    private val helpRequestRepository: HelpRequestRepository,
    private val helpProposalRepository: HelpProposalRepository,
    private val navigator: Navigator
) : ViewModel() {

    // Values
    private val userId = Firebase.auth.currentUser?.uid
        ?: throw Exception("No authenticated user found")

    // State Flows
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _helpRequests = MutableStateFlow(emptyList<HelpRequest>())
    val helpRequests = _helpRequests.asStateFlow()

    private val _helpProposals = MutableStateFlow(emptyList<HelpProposal>())
    val helpProposals = _helpProposals.asStateFlow()

    // Init
    init {
        fetchAllData()
    }

    // Functions

    /**
     * Fetches user, help requests, and help proposals from the repositories
     */
    private fun fetchAllData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Fetch user data
                userRepository.getById(userId).collect { user ->
                    _user.value = user
                    // Fetch data based on user type
                    if (user != null) {
                        when (user.type) {
                            UserType.Victim -> {
                                helpRequestRepository.getByVictimId(userId).collect { helpRequests ->
                                    _helpRequests.value = helpRequests
                                }
                                _helpProposals.value = emptyList()
                            }
                            UserType.Supporter -> {
                                helpProposalRepository.getBySupporterId(userId).collect { helpProposals ->
                                    _helpProposals.value = helpProposals
                                }
                                _helpRequests.value = emptyList()
                            }
                            else -> {
                                _helpRequests.value = emptyList()
                                _helpProposals.value = emptyList()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to load data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        _isLoading.value = false
    }

    /**
     * Logs user out and navigates to the welcome screen
     */
    fun logOut() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            SnackbarController.showSnackbar("Logged out successfully")
            navigator.replaceAll(WelcomeScreen())
        }
    }

    /**
     * Logs user out of current disaster by updating user's natural disaster
     * to null and type to UserType.Neutral
     */
    fun logOutFromCurrentDisaster() {
        viewModelScope.launch {
            try {
                userRepository.update(user.value!!.copy(naturalDisaster = null, type = UserType.Neutral))
                _user.value = user.value!!.copy(naturalDisaster = null, type = UserType.Neutral)
            } catch (e: Exception) {
                _error.value = "Failed to log out from current disaster: ${e.message}"
            } finally {
                SnackbarController.showSnackbar("Logged out from disaster successfully")
            }
        }
    }
}