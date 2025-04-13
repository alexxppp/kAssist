package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.victim.HelpRequest
import kotlinx.coroutines.flow.Flow

interface HelpRequestRepository {

    fun getHelpRequests(): Flow<List<HelpRequest>>
    fun getHelpRequestById(id: String): Flow<HelpRequest?>
    suspend fun addHelpRequest(helpRequest: HelpRequest)
    suspend fun deleteHelpRequest(helpRequest: HelpRequest)

}