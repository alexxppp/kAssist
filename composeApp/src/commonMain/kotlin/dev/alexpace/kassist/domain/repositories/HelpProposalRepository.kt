package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import kotlinx.coroutines.flow.Flow

interface HelpProposalRepository {

    fun getAllByDisaster(disasterId: String): Flow<List<HelpProposal>>
    fun getById(id: String): Flow<HelpProposal?>
    fun getBySupporterId(id: String): Flow<List<HelpProposal>>
    fun getByVictimId(id: String): Flow<List<HelpProposal?>>
    fun getBySupporterIdAndStatuses(id: String, statuses: List<RequestStatusTypes>): Flow<List<HelpProposal>>
    fun getByVictimIdAndStatuses(id: String, statuses: List<RequestStatusTypes>): Flow<List<HelpProposal>>
    suspend fun add(helpProposal: HelpProposal)
    suspend fun update(helpProposal: HelpProposal)
    suspend fun delete(helpProposal: HelpProposal)

}