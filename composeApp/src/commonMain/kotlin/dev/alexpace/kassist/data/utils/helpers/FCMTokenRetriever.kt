package dev.alexpace.kassist.data.utils.helpers

import dev.gitlive.firebase.messaging.FirebaseMessaging

object FCMTokenRetriever {
    suspend fun getFCMToken(messaging: FirebaseMessaging): String {
        return messaging.getToken()
    }
}