package dev.alexpace.kassist

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.data.repositoriesImpl.EmergencyPlanRepositoryImpl
import dev.alexpace.kassist.ui.navigation.screens.shared.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    // TODO: Pass later to screens
    val emergencyPlanRepository = remember { EmergencyPlanRepositoryImpl() }

    MaterialTheme {
        Navigator(screen = HomeScreen())
    }

}