package dev.alexpace.kassist.data.network.servicesImpl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dev.alexpace.kassist.domain.models.shared.Coordinates
import kotlinx.coroutines.tasks.await

actual class LocationServiceImpl(private val context: Context) {

    private val locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    actual suspend fun getCurrentLocation(): Coordinates? {
        // Check permissions
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        return try {
            val location = locationClient.lastLocation.await()
            location?.let { Coordinates(it.latitude, it.longitude) }
        } catch (e: Exception) {
            null
        }
    }

}