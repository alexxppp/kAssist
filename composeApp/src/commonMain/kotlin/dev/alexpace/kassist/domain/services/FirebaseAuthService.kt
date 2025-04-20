package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.domain.models.shared.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthService {

    val currentUserId: String
    val isAuthenticated: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun register(email: String, password: String)

    suspend fun signOut()
}

expect suspend fun registerWithFirebase(email: String, password: String)