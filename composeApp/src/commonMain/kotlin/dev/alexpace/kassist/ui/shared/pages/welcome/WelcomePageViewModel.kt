package dev.alexpace.kassist.ui.shared.pages.welcome

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.ui.shared.navigation.screens.LoginScreen
import dev.alexpace.kassist.ui.shared.navigation.screens.RegistrationScreen

class WelcomePageViewModel : ViewModel() {



    fun onRegisterClick(navigator: Navigator) {
        navigator.push(RegistrationScreen())
    }

    fun onLoginClick(navigator: Navigator) {
        navigator.push(LoginScreen())
    }
}