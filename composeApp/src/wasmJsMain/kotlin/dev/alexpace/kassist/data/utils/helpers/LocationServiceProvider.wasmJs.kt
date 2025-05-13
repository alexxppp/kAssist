package dev.alexpace.kassist.data.utils.helpers

import dev.alexpace.kassist.data.network.servicesImpl.LocationServiceImpl

actual object LocationServiceProvider {
    actual fun getLocationService(): LocationServiceImpl {
        return LocationServiceImpl()
    }
}