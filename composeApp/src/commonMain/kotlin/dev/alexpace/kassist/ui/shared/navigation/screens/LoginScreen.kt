package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.ui.shared.pages.login.LoginPage

class LoginScreen : Screen {

    // Content
    @Composable
    override fun Content() {
        LoginPage()
    }
}