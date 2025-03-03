package dev.alexpace.kassist.navigation.tabs.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.TabOptions

@Composable
fun homeTabOptions(): TabOptions {
    val title = "Home"
    val icon = rememberVectorPainter(Icons.Default.Home)
    return TabOptions(
        index = 0u,
        title = title,
        icon = icon
    )
}

@Composable
fun mapTabOptions(): TabOptions {
    val title = "Map"
    val icon = rememberVectorPainter(Icons.Default.Place)
    return TabOptions(
        index = 0u,
        title = title,
        icon = icon
    )
}

@Composable
fun profileTabOptions(): TabOptions {
    val title = "Profile"
    val icon = rememberVectorPainter(Icons.Default.Person)
    return TabOptions(
        index = 0u,
        title = title,
        icon = icon
    )
}