package dev.alexpace.kassist.ui.shared.pages.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(markerTitle: String?, lat: Double?, lon: Double?) {
    val coord = CLLocationCoordinate2DMake(lat!!, lon!!)
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            MKMapView().apply {
                // Enable zooming and panning
                setZoomEnabled(true)
                setScrollEnabled(true)
            }
        },
        update = { mapView ->
            // Remove old annotations and add a new one
            mapView.removeAnnotations(mapView.annotations)
            val annotation = MKPointAnnotation().apply {
                setCoordinate(coord)
            }
            mapView.addAnnotation(annotation)
            mapView.setCenterCoordinate(coord, animated = true)
        }
    )
}