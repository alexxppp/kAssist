package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.data.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.ui.shared.pages.login.LoginPage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class LoginScreen : Screen {

    private val authService = FirebaseAuthServiceImpl(Firebase.auth)

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        LoginPage(authService, onLoginSuccess(navigator))
    }

    private fun onLoginSuccess(navigator: Navigator): () -> Unit = {
        navigator.push(HomeScreen())
    }

}