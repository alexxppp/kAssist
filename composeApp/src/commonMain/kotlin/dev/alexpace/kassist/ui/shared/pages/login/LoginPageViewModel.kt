package dev.alexpace.kassist.ui.shared.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.ui.shared.navigation.screens.HomeScreen
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class LoginPageViewModel(
    private val authService: FirebaseAuthService,
    private val navigator: Navigator
) : ViewModel() {

    // Values
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoginButtonEnabled = MutableStateFlow(false)
    val isLoginButtonEnabled = _isLoginButtonEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Init
    init {
        handleLoginButtonState()
    }

    /**
     * Handles login button state based on email and password
     * If not valid, button is disabled
     */
    private fun handleLoginButtonState() {
        viewModelScope.launch {
            email.combine(password) { email, password ->
                email.isNotBlank() && password.length >= 6
            }.collect { isEnabled ->
                _isLoginButtonEnabled.value = isEnabled
            }
        }
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    /**
     * Logs user in with email and password by calling the AuthService
     */
    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                authService.authenticate(_email.value, _password.value)
                navigator.replaceAll(HomeScreen())
            } catch (e: Exception) {
                _errorMessage.value = "Invalid email or password"
            } finally {
                _isLoading.value = false
                SnackbarController.showSnackbar("Logged in successfully!")
            }
        }
    }
}