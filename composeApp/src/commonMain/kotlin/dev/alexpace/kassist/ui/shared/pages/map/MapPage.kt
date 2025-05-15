package dev.alexpace.kassist.ui.shared.pages.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.alexpace.kassist.ui.shared.components.map.MapView
import org.koin.compose.koinInject

@Composable
fun MapPage() {
    // DI
    val usersLocationRepository = koinInject<UsersLocationRepository>()

    // ViewModel
    val viewModel = viewModel {
        MapPageViewModel(
            usersLocationRepository
        )
    }

    // Collect markers from ViewModel
    val markers = viewModel.markers.collectAsState().value

    // UI
    MapView(
        markers = markers,
        initialLat = 37.7749,
        initialLon = -122.4194,
        zoomLevel = 12f
    )
}