package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.supporter.components.proposal.HelpProposalForm
import dev.alexpace.kassist.ui.supporter.components.requests.HelpRequestList
import org.koin.compose.koinInject

@Composable
fun SupporterHelpPage() {
    // DI
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val helpProposalRepository = koinInject<HelpProposalRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel: SupporterHelpPageViewModel = viewModel {
        SupporterHelpPageViewModel(helpRequestRepository, helpProposalRepository, userRepository)
    }

    val helpRequests = viewModel.helpRequests.collectAsState().value

    // UI
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HelpRequestList(helpRequests, viewModel::selectHelpRequest)
            viewModel.selectedHelpRequest.collectAsState().value?.let { request ->
                HelpProposalForm(
                    helpRequest = request,
                    onSubmit = { content ->
                        viewModel.submitHelpProposal(content, request)
                    },
                    onCancel = { viewModel.selectHelpRequest(null) }
                )
            }
        }
    }
}