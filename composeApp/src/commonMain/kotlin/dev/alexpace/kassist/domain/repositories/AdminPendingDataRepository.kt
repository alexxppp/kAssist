package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.enums.nds.NeedLevelTypes
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import kotlinx.coroutines.flow.Flow

interface AdminPendingDataRepository {

    fun getAllPendingHelpRequestsByDisaster(disasterId: Int): Flow<List<HelpRequest>>
    suspend fun acceptHelpRequest(helpRequest: HelpRequest, needLevel: NeedLevelTypes)
    suspend fun rejectOrDeleteHelpRequest(helpRequestId: String)

}