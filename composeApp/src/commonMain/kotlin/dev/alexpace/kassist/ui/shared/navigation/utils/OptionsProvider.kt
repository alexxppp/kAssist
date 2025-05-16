package dev.alexpace.kassist.ui.shared.navigation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
fun contactTabOptions(): TabOptions {
    val title = "Contacts"
    val icon = rememberVectorPainter(Icons.Default.Email)
    return TabOptions(
        index = 1u,
        title = title,
        icon = icon
    )
}

@Composable
fun helpTabOptions(): TabOptions {
    val title = "Help"
    val icon = rememberVectorPainter(Icons.Default.Person)
    return TabOptions(
        index = 2u,
        title = title,
        icon = icon
    )
}

@Composable
fun infoTabOptions(): TabOptions {
    val title = "Info"
    val icon = rememberVectorPainter(Icons.Default.Info)
    return TabOptions(
        index = 3u,
        title = title,
        icon = icon
    )
}

// Admin

@Composable
fun adminModeratorTabOptions(): TabOptions {
    val title = "Moderate"
    val icon = rememberVectorPainter(Icons.Default.Home)
    return TabOptions(
        index = 0u,
        title = title,
        icon = icon
    )
}

@Composable
fun adminUserTabOptions(): TabOptions {
    val title = "Users"
    val icon = rememberVectorPainter(Icons.Default.Person)
    return TabOptions(
        index = 1u,
        title = title,
        icon = icon
    )
}