package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.victim.HelpRequest
import kotlinx.coroutines.flow.Flow

interface HelpRequestRepository {

    fun getAll(): Flow<List<HelpRequest>>
    fun getById(id: String): Flow<HelpRequest?>
    fun getByVictimId(id: String): Flow<List<HelpRequest>>
    fun getAllByDisaster(disasterId: Int): Flow<List<HelpRequest>>
    suspend fun add(helpRequest: HelpRequest)
    suspend fun addPending(helpRequest: HelpRequest)
    suspend fun update(helpRequest: HelpRequest)
    suspend fun delete(helpRequest: HelpRequest)

}