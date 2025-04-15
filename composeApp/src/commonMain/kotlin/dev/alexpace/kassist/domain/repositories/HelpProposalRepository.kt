package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import kotlinx.coroutines.flow.Flow

interface HelpProposalRepository {

    fun getAll(): Flow<List<HelpProposal>>
    fun getById(id: String): Flow<HelpProposal?>
    fun getBySupporterId(id: String): Flow<List<HelpProposal>>
    fun getByVictimId(id: String): Flow<List<HelpProposal?>>
    suspend fun add(helpProposal: HelpProposal)
    suspend fun delete(helpProposal: HelpProposal)

}