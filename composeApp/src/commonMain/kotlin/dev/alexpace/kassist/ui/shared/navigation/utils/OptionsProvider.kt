package dev.alexpace.kassist.ui.shared.navigation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
fun helpTabOptions(): TabOptions {
    val title = "Help"
    val icon = rememberVectorPainter(Icons.Default.Person) // TODO: Change Icon
    return TabOptions(
        index = 1u,
        title = title,
        icon = icon
    )
}