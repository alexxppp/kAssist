package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.classes.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAll(): Flow<List<User>>
    fun getById(id: String): Flow<User?>
    suspend fun add(user: User)
    suspend fun update(user: User)
    suspend fun delete(id: String)
}