package dev.alexpace.kassist.ui.shared.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dev.alexpace.kassist.domain.models.shared.MapMarker

@Composable
actual fun MapView(
    markers: List<MapMarker>,
    initialLat: Double?,
    initialLon: Double?,
    zoomLevel: Float
) {
    val initialLocation = if (initialLat != null && initialLon != null) {
        LatLng(initialLat, initialLon)
    } else if (markers.isNotEmpty()) {
        LatLng(markers[0].lat, markers[0].lon)
    } else {
        LatLng(0.0, 0.0)
    }

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, zoomLevel)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState
    ) {
        markers.forEach { marker ->
            Marker(
                state = rememberMarkerState(position = LatLng(marker.lat, marker.lon)),
                title = marker.title,
                icon = marker.color?.let { hue ->
                    BitmapDescriptorFactory.defaultMarker(hue)
                }
            )
        }
    }
}