package dev.alexpace.kassist

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.ui.shared.components.app.AppScaffold
import dev.alexpace.kassist.ui.shared.navigation.screens.WelcomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = WelcomeScreen()) {navigator: Navigator ->
            AppScaffold(screen = navigator.lastItem)
        }
    }
}
