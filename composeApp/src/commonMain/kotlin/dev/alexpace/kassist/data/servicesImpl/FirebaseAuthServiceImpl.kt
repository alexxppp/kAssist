package dev.alexpace.kassist.data.servicesImpl

import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseAuthServiceImpl() : FirebaseAuthService {

    val auth = Firebase.auth

    val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override val currentUserId: String
        get() = auth.currentUser?.uid.toString()

    override val isAuthenticated: Boolean
        get() = auth.currentUser != null && auth.currentUser?.isAnonymous == false

    override val currentUser: Flow<User> =
        auth.authStateChanged
            .map {
                it?.let {
                    User(it.uid, it.isAnonymous, it.email ?: "", it.displayName ?: "", it.phoneNumber ?: "")
                }
                    ?: User("", false, "", "", "")
            }

    private suspend fun launchWithAwait(block: suspend () -> Unit) {
        scope.async {
            block()
        }.await()
    }

    override suspend fun authenticate(email: String, password: String) {
        launchWithAwait {
            auth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun register(email: String, password: String) {
        launchWithAwait {
            auth.createUserWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signOut() {

        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }

        auth.signOut()
    }
}