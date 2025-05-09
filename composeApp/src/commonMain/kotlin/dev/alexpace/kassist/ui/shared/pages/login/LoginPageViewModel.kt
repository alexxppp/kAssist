package dev.alexpace.kassist.ui.shared.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class LoginPageViewModel(private val authService: FirebaseAuthService) : ViewModel() {
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

    init {
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

    fun login(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                authService.authenticate(_email.value, _password.value)
                onLoginSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Invalid email or password"
            } finally {
                _isLoading.value = false
            }
        }
    }
}