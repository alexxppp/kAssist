package dev.alexpace.kassist.ui.admin.pages.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DashboardPageViewModel(
    private val userRepository: UserRepository,
    private val adminPendingDataRepository: AdminPendingDataRepository
) : ViewModel() {

    // Values
    val currentUserId = Firebase.auth.currentUser?.uid
    // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    // State Flows
    private val _dashboardPageState = MutableStateFlow(DashboardPageState())
    val dashboardPageState = _dashboardPageState.asStateFlow()

    // Init
    init {
        fetchUser()
        fetchHelpRequests()
    }

    // Functions

    /**
     * Fetches user
     */
    private fun fetchUser() {
        viewModelScope.launch {
            try {
                _dashboardPageState.update {
                    it.copy(
                        user = userRepository.getById(currentUserId).firstOrNull()
                    )
                }
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
            }
        }
    }

    /**
     * Fetches all help requests by disasterId and keeps them updated
     * with the current db state
     */
    private fun fetchHelpRequests() {
        if (_dashboardPageState.value.user == null
            || _dashboardPageState.value.user!!.naturalDisaster == null
        )
            return

        viewModelScope.launch {
            try {
                adminPendingDataRepository.getAllHelpRequestsByDisaster(
                    _dashboardPageState.value.user!!.naturalDisaster!!.id
                ).collectLatest { helpRequests ->
                    _dashboardPageState.update {
                        it.copy(helpRequests = helpRequests)
                    }
                }
            } catch (e: Exception) {
                println("Error fetching help requests: ${e.message}")
                _dashboardPageState.update {
                    it.copy(helpRequests = emptyList())
                }
            }
        }
    }
}