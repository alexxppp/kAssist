package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.data.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.ui.shared.pages.registration.RegistrationPage
import org.koin.compose.koinInject

class RegistrationScreen : Screen {


    @Composable
    override fun Content() {
        val authService = koinInject<FirebaseAuthServiceImpl>()
        val navigator = LocalNavigator.currentOrThrow
        RegistrationPage(authService, onRegisterSuccess(navigator))
    }

    private fun onRegisterSuccess(navigator: Navigator): () -> Unit = {
        navigator.push(LoginScreen())
    }

}