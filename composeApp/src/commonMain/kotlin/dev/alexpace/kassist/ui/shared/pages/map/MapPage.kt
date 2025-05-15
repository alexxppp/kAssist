package dev.alexpace.kassist.ui.shared.pages.map

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.models.shared.MapMarker
import dev.alexpace.kassist.ui.shared.components.map.MapView

@Composable
fun MapPage() {

    // Values
    val markers = listOf(
        MapMarker(title = "Point A", lat = 37.7749, lon = -122.4194),
        MapMarker(title = "Point B", lat = 37.7849, lon = -122.4094)
    )

    // DI


    // ViewModel
    val viewModel = viewModel {
        MapPageViewModel()
    }


    // UI
    MapView(
        markers = markers,
        initialLat = 37.7749,
        initialLon = -122.4194,
        zoomLevel = 12f
    )
}