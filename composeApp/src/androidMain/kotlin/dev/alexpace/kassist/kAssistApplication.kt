package dev.alexpace.kassist

import android.app.Application
import dev.alexpace.kassist.data.background.TrackUserServiceImpl
import dev.alexpace.kassist.data.utils.di.sharedModule
import dev.alexpace.kassist.data.utils.helpers.LocationServiceProvider
import dev.alexpace.kassist.domain.services.TrackUserService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class kAssistApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(sharedModule)
        }
        LocationServiceProvider.initialize(applicationContext)
        val trackUserService by inject<TrackUserService>()
        trackUserService.launchTrackingUser()
    }

    override fun onTerminate() {
        super.onTerminate()
        TrackUserServiceImpl().stopTrackingUser()
    }
}