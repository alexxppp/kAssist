package dev.alexpace.kassist

import android.app.Application
import com.google.firebase.FirebaseApp

class kAssistApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}