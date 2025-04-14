package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.data.servicesImpl.FirebaseAuthServiceImpl
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.ui.shared.pages.login.LoginPage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class LoginScreen: Screen {

    @Composable
    override fun Content() {

        val authService: FirebaseAuthService = FirebaseAuthServiceImpl(
            Firebase.auth,
        )

        LoginPage(
            authService = authService,
            onLoginSuccess = { /* TODO: Navigate to home screen */ }
        )
    }

}