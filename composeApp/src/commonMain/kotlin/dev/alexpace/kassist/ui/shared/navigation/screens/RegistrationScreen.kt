package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.data.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.ui.shared.pages.registration.RegistrationPage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class RegistrationScreen : Screen {

    private val authService = FirebaseAuthServiceImpl(Firebase.auth)

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        RegistrationPage(authService, onRegisterSuccess(navigator))
    }

    private fun onRegisterSuccess(navigator: Navigator): () -> Unit = {
        navigator.push(LoginScreen())
    }

}