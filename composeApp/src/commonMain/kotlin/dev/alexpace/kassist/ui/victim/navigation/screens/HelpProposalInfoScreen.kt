package dev.alexpace.kassist.ui.victim.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.ui.victim.pages.proposalInfo.ProposalInfoPage

class HelpProposalInfoScreen(
    private val proposal: HelpProposal
): Screen {

    override val key = proposal.id

    // Content
    @Composable
    override fun Content() {
        ProposalInfoPage(proposal)
    }
}