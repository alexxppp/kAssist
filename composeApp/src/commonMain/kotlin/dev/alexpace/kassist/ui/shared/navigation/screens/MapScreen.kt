package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.ui.shared.pages.map.MapView

class MapScreen: Screen {
    @Composable
    override fun Content() {
        MapView("lala", 1.54, 12.1)
    }
}