package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.ui.shared.pages.map.MapPage

class MapScreen: Screen {
    @Composable
    override fun Content() {
        MapPage()
    }
}