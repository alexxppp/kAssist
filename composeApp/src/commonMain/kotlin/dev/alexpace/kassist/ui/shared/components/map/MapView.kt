package dev.alexpace.kassist.ui.shared.components.map

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.domain.models.classes.map.MapMarker

@Composable
expect fun MapView(
    markers: List<MapMarker> = emptyList(),
    initialLat: Double? = null,
    initialLon: Double? = null,
    zoomLevel: Float = 10f
)