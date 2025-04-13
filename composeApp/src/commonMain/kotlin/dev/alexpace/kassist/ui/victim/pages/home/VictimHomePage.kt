package dev.alexpace.kassist.ui.victim.pages.home

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl

@Composable
fun VictimHomePage() {

    val helpProposalRepository = HelpProposalRepositoryImpl()

    VictimHomePageContent(helpProposalRepository)

}
