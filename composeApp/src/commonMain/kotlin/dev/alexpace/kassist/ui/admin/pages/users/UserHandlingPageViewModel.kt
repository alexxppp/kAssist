package dev.alexpace.kassist.ui.admin.pages.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class UserHandlingPageViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // State Flows
    private val _usersWithNegativeScore = MutableStateFlow<List<User?>>(emptyList())
    val usersWithNegativeScore = _usersWithNegativeScore.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    // Init
    init {
        fetchUsersWithNegativeScore()
    }

    // Functions

    /**
     * Fetches all users with negative scores
     */
    private fun fetchUsersWithNegativeScore() {
        viewModelScope.launch {
            try {
                userRepository.getAllWithNegativeScore().collectLatest { users ->
                    _usersWithNegativeScore.value = users
                    _isLoading.value = false
                    _error.value = null
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Error fetching users: ${e.message}"
            }
        }
    }

    /**
     * Sends a warning to the specified user
     */
    fun sendWarningToUser(userId: String) {
        viewModelScope.launch {
            try {
                // Implement warning logic here
                // For example: userRepository.sendWarning(userId)
            } catch (e: Exception) {
                _error.value = "Error sending warning: ${e.message}"
            }
        }
    }

    /**
     * Bans the specified user
     */
    fun banUser(userId: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.getById(userId).firstOrNull()

                val bannedUser = user!!.copy(role = UserRole.Banned)

                userRepository.update(bannedUser)

                fetchUsersWithNegativeScore()
            } catch (e: Exception) {
                _error.value = "Error banning user: ${e.message}"
            }
        }
    }
}