package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.domain.models.classes.user.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthService {

    val currentUserId: String
    val isAuthenticated: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun register(email: String, password: String)

    suspend fun signOut()
}

