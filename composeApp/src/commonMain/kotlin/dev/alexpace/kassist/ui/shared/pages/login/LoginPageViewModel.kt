package dev.alexpace.kassist.ui.shared.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.domain.services.TrackUserService
import dev.alexpace.kassist.ui.shared.navigation.screens.HomeScreen
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginPageViewModel(
    private val authService: FirebaseAuthService,
    private val navigator: Navigator
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(LoginPageState())
    val state = _state.asStateFlow()

    init {
        handleLoginButtonState()
    }

    /**
     * Handles login button state based on email and password
     * If not valid, button is disabled
     */
    private fun handleLoginButtonState() {
        viewModelScope.launch {
            _state.collect { state ->
                _state.value = state.copy(
                    isLoginButtonEnabled = state.email.isNotBlank() && state.password.length >= 6
                )
            }
        }
    }

    fun updateEmail(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    /**
     * Logs user in with email and password by calling the AuthService
     */
    fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            try {
                authService.authenticate(_state.value.email, _state.value.password)
                navigator.replaceAll(HomeScreen())
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMessage = "Invalid email or password")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
                SnackbarController.showSnackbar("Logged in successfully!")
                val trackUserService by inject<TrackUserService>()
                trackUserService.launchTrackingUser()
            }
        }
    }
}