package dev.alexpace.kassist.ui.shared.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import dev.alexpace.kassist.domain.models.classes.map.MapMarker
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation
import platform.MapKit.MKCoordinateRegionMakeWithDistance

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(
    markers: List<MapMarker>,
    initialLat: Double?,
    initialLon: Double?,
    zoomLevel: Float
) {
    val initialCoord = if (initialLat != null && initialLon != null) {
        CLLocationCoordinate2DMake(initialLat, initialLon)
    } else if (markers.isNotEmpty()) {
        CLLocationCoordinate2DMake(markers[0].lat, markers[0].lon)
    } else {
        CLLocationCoordinate2DMake(0.0, 0.0)
    }

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            MKMapView().apply {
                setZoomEnabled(true)
                setScrollEnabled(true)
                // Convert zoomLevel to distance (approximate, adjust as needed)
                val distance = 100000.0 / zoomLevel.toDouble() // Example conversion
                setRegion(
                    MKCoordinateRegionMakeWithDistance(initialCoord, distance, distance),
                    animated = false
                )
            }
        },
        update = { mapView ->
            mapView.removeAnnotations(mapView.annotations)
            markers.forEach { marker ->
                val annotation = MKPointAnnotation().apply {
                    setCoordinate(CLLocationCoordinate2DMake(marker.lat, marker.lon))
                    setTitle(marker.title)
                }
                mapView.addAnnotation(annotation)
            }
            mapView.setCenterCoordinate(initialCoord, animated = true)
        }
    )
}