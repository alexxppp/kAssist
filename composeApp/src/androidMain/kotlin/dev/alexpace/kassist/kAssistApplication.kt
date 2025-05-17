package dev.alexpace.kassist

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
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
        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground,
                showPushNotification = true,
            )
        )
        LocationServiceProvider.initialize(applicationContext)
        val trackUserService by inject<TrackUserService>()
        trackUserService.launchTrackingUser()
    }

    override fun onTerminate() {
        super.onTerminate()
        TrackUserServiceImpl().stopTrackingUser()
    }
}