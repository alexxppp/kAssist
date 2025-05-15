package dev.alexpace.kassist

import android.app.Application
import dev.alexpace.kassist.data.background.TrackUserServiceImpl
import dev.alexpace.kassist.data.utils.di.sharedModule
import dev.alexpace.kassist.data.utils.helpers.LocationServiceProvider
import org.koin.core.context.startKoin

class kAssistApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(sharedModule)
        }
        LocationServiceProvider.initialize(applicationContext)
        TrackUserServiceImpl().launchTrackingUser()
    }

    override fun onTerminate() {
        super.onTerminate()
        TrackUserServiceImpl().stopTrackingUser()
    }
}