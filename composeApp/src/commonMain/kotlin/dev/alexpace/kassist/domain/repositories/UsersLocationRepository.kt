package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.classes.map.UserLocation
import kotlinx.coroutines.flow.Flow

interface UsersLocationRepository {

    fun getAll(): Flow<List<UserLocation>>
    fun getAllByDisaster(ndId: String): Flow<List<UserLocation>>
    fun getById(id: String): Flow<UserLocation>
    suspend fun exists(userLocationId: String): Boolean
    suspend fun add(userLocation: UserLocation)
    suspend fun update(userLocation: UserLocation)
    suspend fun delete(id: String)
    suspend fun populateUserLocations()
}