package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.ui.shared.pages.welcome.WelcomePage

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        // Declaring non-nullable navigator
        val navigator = LocalNavigator.currentOrThrow

        WelcomePage(navigator)
    }
}