package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import kotlinx.coroutines.flow.Flow

interface HelpProposalRepository {

    fun getHelpProposals(): Flow<List<HelpProposal>>
    fun getHelpProposalById(id: String): Flow<HelpProposal?>
    suspend fun addHelpProposal(helpProposal: HelpProposal)
    suspend fun deleteHelpProposal(helpProposal: HelpProposal)

}