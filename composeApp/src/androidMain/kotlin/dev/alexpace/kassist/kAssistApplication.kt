package dev.alexpace.kassist

import android.app.Application
import dev.alexpace.kassist.data.utils.di.sharedModule
import org.koin.core.context.startKoin

class kAssistApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(sharedModule)
        }
    }
}