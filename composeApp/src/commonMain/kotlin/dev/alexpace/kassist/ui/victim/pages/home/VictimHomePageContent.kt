package dev.alexpace.kassist.ui.victim.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.ui.victim.components.proposals.ProposalList

@Composable
fun VictimHomePageContent(helpProposalRepository: HelpProposalRepository) {
    // Collect the list of help proposals from the repository as a state
    val allProposals by helpProposalRepository.getHelpProposals().collectAsState(initial = emptyList())

    // Placeholder HelpRequest, as no HelpRequestRepository is provided
    val placeholderHelpRequest = HelpRequest(
        id = "",
        victimId = "",
        victimName = "Unknown",
        address = "Unknown",
        description = "No request data available",
        needLevel = NeedLevelTypes.Low,
        status = RequestStatusTypes.Pending
    )

    // Create a list of pairs using the placeholder HelpRequest
    val proposalsWithRequests = allProposals.map { proposal ->
        proposal to placeholderHelpRequest
    }

    // Use the provided ProposalList composable
    ProposalList(proposalsWithRequests = proposalsWithRequests)
}