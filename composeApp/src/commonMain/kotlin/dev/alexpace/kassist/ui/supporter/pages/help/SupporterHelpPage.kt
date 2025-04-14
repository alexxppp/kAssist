package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.supporter.components.requests.HelpRequestList
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.compose.koinInject

@Composable
fun SupporterHelpPage() {
    val helpRequestRepository = koinInject<HelpRequestRepositoryImpl>()
    val helpProposalRepository = koinInject<HelpProposalRepositoryImpl>()
    val userId = Firebase.auth.currentUser!!.uid

    val viewModel: SupporterHelpPageViewModel = viewModel {
        SupporterHelpPageViewModel(helpRequestRepository, helpProposalRepository, userId)
    }

    HelpRequestList(viewModel)
}