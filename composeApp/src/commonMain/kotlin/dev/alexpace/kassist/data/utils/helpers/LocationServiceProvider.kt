package dev.alexpace.kassist.data.utils.helpers

import dev.alexpace.kassist.data.network.servicesImpl.LocationServiceImpl

expect object LocationServiceProvider {
    fun getLocationService(): LocationServiceImpl
}