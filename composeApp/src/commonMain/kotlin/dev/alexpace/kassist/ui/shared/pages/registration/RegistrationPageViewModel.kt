package dev.alexpace.kassist.ui.shared.pages.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RegistrationPageViewModel(
    val authService: FirebaseAuthService
): ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isRegistrationButtonEnabled = MutableStateFlow(false)
    val isRegistrationButtonEnabled = _isRegistrationButtonEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            combine(email, password, confirmPassword) { email, password, confirmPassword ->
                email.isNotBlank() && password.length >= 6
                        && password == confirmPassword
            }.collect { isEnabled ->
                _isRegistrationButtonEnabled.value = isEnabled
            }
        }
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newConfirmPassword: String) {
        _password.value = newConfirmPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun register(onRegisterSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                authService.register(_email.value, _password.value)
                onRegisterSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Invalid email or password"
            } finally {
                _isLoading.value = false
            }
        }
    }
}