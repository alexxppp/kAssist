package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import kotlinx.coroutines.flow.Flow

interface AdminPendingDataRepository {

    fun getAllHelpRequestsByDisaster(disasterId: Int): Flow<List<HelpRequest>>
    suspend fun acceptHelpRequest(helpRequest: HelpRequest, needLevel: NeedLevelTypes)
    suspend fun rejectOrDeleteHelpRequest(helpRequestId: String)

}