package dev.alexpace.kassist.data.network.servicesImpl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dev.alexpace.kassist.domain.models.shared.Coordinates
import kotlinx.coroutines.tasks.await

actual class LocationServiceImpl(private val context: Context) {

    private val locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    actual suspend fun getCurrentLocation(): Coordinates? {
        // Check if location services are enabled
        if (!isLocationEnabled()) {
            return null
        }

        // Check permissions
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocation && !hasCoarseLocation) {
            return null
        }

        return try {
            // Try to get last known location
            val location = locationClient.lastLocation.await()
            if (location != null) {
                return Coordinates(location.latitude, location.longitude)
            }

            // If last location is null, request a fresh location
            val freshLocation = requestFreshLocation()
            freshLocation?.let { Coordinates(it.latitude, it.longitude) }
        } catch (e: SecurityException) {
            // Handle permission-related errors
            null
        } catch (e: Exception) {
            // Handle other errors
            null
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private suspend fun requestFreshLocation(): Location? {
        return try {
            val locationTask = locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            )
            locationTask.await()
        } catch (e: Exception) {
            null
        }
    }
}