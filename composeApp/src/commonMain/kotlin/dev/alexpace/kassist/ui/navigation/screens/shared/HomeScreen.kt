package dev.alexpace.kassist.ui.navigation.screens.shared

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.ui.pages.shared.HomePage

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        // Declaring non-nullable navigator
        val navigator = LocalNavigator.currentOrThrow
        HomePage(navigator)
    }
}