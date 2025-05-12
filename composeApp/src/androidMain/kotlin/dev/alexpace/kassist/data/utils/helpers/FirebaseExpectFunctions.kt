package dev.alexpace.kassist.data.utils.helpers

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

actual suspend fun registerWithFirebase(email: String, password: String) {
    Firebase.auth.createUserWithEmailAndPassword(email, password)
}