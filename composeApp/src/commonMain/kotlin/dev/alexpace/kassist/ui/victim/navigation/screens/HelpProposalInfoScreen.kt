package dev.alexpace.kassist.ui.victim.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.ui.victim.pages.proposalInfo.ProposalInfoPage

class HelpProposalInfoScreen(
    private val proposalId: String
): Screen {
    @Composable
    override fun Content() {
        ProposalInfoPage(proposalId)
    }
}