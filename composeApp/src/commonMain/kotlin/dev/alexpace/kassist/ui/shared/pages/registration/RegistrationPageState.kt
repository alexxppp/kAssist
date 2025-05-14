package dev.alexpace.kassist.ui.shared.pages.registration

data class RegistrationPageState(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isAdmin: Boolean = false,
    val isRegistrationButtonEnabled: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)