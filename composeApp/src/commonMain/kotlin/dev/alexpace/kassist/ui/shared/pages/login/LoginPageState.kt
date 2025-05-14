package dev.alexpace.kassist.ui.shared.pages.login

data class LoginPageState (
    val email: String = "",
    val password: String = "",
    val isLoginButtonEnabled: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)