package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.domain.models.classes.map.Coordinates

expect class LocationServiceImpl {

    suspend fun getCurrentLocation(): Coordinates?

}