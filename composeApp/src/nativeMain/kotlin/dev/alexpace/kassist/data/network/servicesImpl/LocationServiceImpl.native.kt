package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.domain.models.shared.Coordinates
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class LocationServiceImpl {
    private val locationManager = CLLocationManager()

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getCurrentLocation(): Coordinates? = suspendCancellableCoroutine { continuation ->
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = didUpdateLocations.lastOrNull() as? CLLocation
                location?.coordinate?.useContents {
                    continuation.resume(Coordinates(latitude, longitude))
                } ?: continuation.resume(null)
                manager.stopUpdatingLocation()
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                continuation.resumeWithException(Exception(didFailWithError.localizedDescription))
                manager.stopUpdatingLocation()
            }
        }

        locationManager.delegate = delegate
        locationManager.desiredAccuracy = 100.0
        locationManager.requestWhenInUseAuthorization()

        if (CLLocationManager.locationServicesEnabled()) {
            locationManager.startUpdatingLocation()
        } else {
            continuation.resume(null)
        }
    }
}