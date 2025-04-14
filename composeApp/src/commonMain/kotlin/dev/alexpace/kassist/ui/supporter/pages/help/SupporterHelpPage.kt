package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.supporter.components.requests.HelpRequestList

@Composable
fun SupporterHelpPage() {
    val helpRequestRepository = HelpRequestRepositoryImpl()
    val helpProposalRepository = HelpProposalRepositoryImpl()
    val userId = "123456"

    val viewModel: SupporterHelpPageViewModel = viewModel {
        SupporterHelpPageViewModel(helpRequestRepository, helpProposalRepository, userId)
    }

    HelpRequestList(viewModel)
}