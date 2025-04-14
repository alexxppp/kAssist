package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.data.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.ui.shared.pages.login.LoginPage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class LoginScreen: Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val authService: FirebaseAuthService = FirebaseAuthServiceImpl(
            Firebase.auth,
        )

        LoginPage(
            authService = authService,
            onLoginSuccess = { navigator.push(HomeScreen()) }
        )
    }

}