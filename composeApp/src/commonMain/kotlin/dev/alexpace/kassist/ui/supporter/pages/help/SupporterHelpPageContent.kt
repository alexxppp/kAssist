package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.supporter.components.requests.HelpRequestList

@Composable
fun SupporterHelpPageContent(helpRequestRepository: HelpRequestRepositoryImpl, helpProposalRepository: HelpProposalRepositoryImpl) {

    HelpRequestList(helpRequestRepository, helpProposalRepository, "123456")

}