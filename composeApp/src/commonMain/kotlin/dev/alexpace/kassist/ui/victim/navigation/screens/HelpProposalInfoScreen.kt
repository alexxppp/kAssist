package dev.alexpace.kassist.ui.victim.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.ui.victim.pages.proposalInfo.ProposalInfoPage

class HelpProposalInfoScreen(
    private val proposal: HelpProposal
): Screen {

    // Content
    @Composable
    override fun Content() {
        ProposalInfoPage(proposal)
    }
}