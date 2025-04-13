package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl

@Composable
fun SupporterHelpPage() {

    val helpRequestRepository = HelpRequestRepositoryImpl()
    val helpProposalRepository = HelpProposalRepositoryImpl()

    SupporterHelpPageContent(helpRequestRepository, helpProposalRepository)

}