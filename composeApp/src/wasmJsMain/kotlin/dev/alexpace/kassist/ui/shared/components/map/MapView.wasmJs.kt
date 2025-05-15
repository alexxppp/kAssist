package dev.alexpace.kassist.ui.shared.components.map

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.domain.models.shared.MapMarker

@Composable
actual fun MapView(
    markers: List<MapMarker>,
    initialLat: Double?,
    initialLon: Double?,
    zoomLevel: Float
) {
}