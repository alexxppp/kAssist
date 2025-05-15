package dev.alexpace.kassist.ui.shared.pages.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.alexpace.kassist.ui.shared.components.map.MapView
import org.koin.compose.koinInject

@Composable
fun MapPage() {
    // DI
    val usersLocationRepository = koinInject<UsersLocationRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel = viewModel {
        MapPageViewModel(
            usersLocationRepository,
            userRepository
        )
    }

    val markers = viewModel.markers.collectAsState().value
    val initialLat = viewModel.initialLat.collectAsState().value
    val initialLon = viewModel.initialLon.collectAsState().value

    // UI
    if (initialLat != null && initialLon != null) {
        MapView(
            markers = markers,
            initialLat = initialLat,
            initialLon = initialLon,
            zoomLevel = 12f
        )
    }
}