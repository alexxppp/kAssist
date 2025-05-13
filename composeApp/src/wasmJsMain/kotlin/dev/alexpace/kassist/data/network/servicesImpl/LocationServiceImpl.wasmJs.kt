package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.domain.models.shared.Coordinates

actual class LocationServiceImpl {
    actual suspend fun getCurrentLocation(): Coordinates? {
        return null
    }
}