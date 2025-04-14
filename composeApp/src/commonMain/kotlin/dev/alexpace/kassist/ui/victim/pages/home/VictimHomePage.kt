package dev.alexpace.kassist.ui.victim.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.ui.victim.components.proposals.ProposalList

@Composable
fun VictimHomePage() {
    val helpProposalRepository = HelpProposalRepositoryImpl()
    val viewModel: VictimHomePageViewModel = viewModel {
        VictimHomePageViewModel(helpProposalRepository)
    }

    val proposalsWithRequests by viewModel.proposalsWithRequests.collectAsState()

    ProposalList(proposalsWithRequests = proposalsWithRequests)
}