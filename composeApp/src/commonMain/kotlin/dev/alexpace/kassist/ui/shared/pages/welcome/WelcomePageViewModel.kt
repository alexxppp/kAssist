package dev.alexpace.kassist.ui.shared.pages.welcome

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.ui.shared.navigation.screens.HomeScreen
import dev.alexpace.kassist.ui.shared.navigation.screens.LoginScreen

class WelcomePageViewModel : ViewModel() {
    fun onGetStartedClick(navigator: Navigator) {
        navigator.push(HomeScreen())
    }

    fun onLoginClick(navigator: Navigator) {
        navigator.push(LoginScreen())
    }
}