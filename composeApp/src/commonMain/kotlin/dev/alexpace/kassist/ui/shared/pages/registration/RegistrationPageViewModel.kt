package dev.alexpace.kassist.ui.shared.pages.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.ui.shared.navigation.screens.LoginScreen
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationPageViewModel(
    val authService: FirebaseAuthService,
    val userRepository: UserRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationPageState())
    val state = _state.asStateFlow()

    fun updateName(newName: String) {
        _state.value = _state.value.copy(name = newName)
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = newPhoneNumber)
    }

    fun updateEmail(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
        updateRegistrationButtonState()
    }

    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
        updateRegistrationButtonState()
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = newConfirmPassword)
        updateRegistrationButtonState()
    }

    fun updateIsAdmin(isAdmin: Boolean) {
        _state.value = _state.value.copy(isAdmin = isAdmin)
    }

    private fun updateRegistrationButtonState() {
        _state.value = _state.value.copy(
            isRegistrationButtonEnabled = _state.value.email.isNotBlank() &&
                    _state.value.password.length >= 6 &&
                    _state.value.password == _state.value.confirmPassword
        )
    }

    fun register() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            try {
                authService.register(_state.value.email, _state.value.password)
                if (Firebase.auth.currentUser != null) {
                    userRepository.add(
                        User(
                            id = Firebase.auth.currentUser!!.uid,
                            email = _state.value.email,
                            name = _state.value.name,
                            phoneNumber = _state.value.phoneNumber,
                            role = if (_state.value.isAdmin) UserRole.Admin else UserRole.Basic
                        )
                    )
                }
                navigator.push(LoginScreen())
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMessage = "Invalid email or password: ${e.message}")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
                SnackbarController.showSnackbar("Registered successfully! Log in to continue.")
            }
        }
    }
}