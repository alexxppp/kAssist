package dev.alexpace.kassist.data.utils.helpers

import android.annotation.SuppressLint
import android.content.Context
import dev.alexpace.kassist.data.network.servicesImpl.LocationServiceImpl

@SuppressLint("StaticFieldLeak")
actual object LocationServiceProvider {
    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }

    actual fun getLocationService(): LocationServiceImpl {
        if (!::context.isInitialized) {
            throw IllegalStateException("LocationServiceProvider must be initialized with a Context")
        }
        return LocationServiceImpl(context)
    }
}