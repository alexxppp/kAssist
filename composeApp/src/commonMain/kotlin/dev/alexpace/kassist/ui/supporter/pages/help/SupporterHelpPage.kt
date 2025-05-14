package dev.alexpace.kassist.ui.supporter.pages.help

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    val isLoading = viewModel.isLoading.collectAsState().value

    // UI
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = Color(0xFF4A90E2),
                strokeWidth = 4.dp
            )
        }
    } else {
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
                        onSubmit = { content, estimatedTime ->
                            viewModel.submitHelpProposal(content, estimatedTime, request)
                        },
                        onCancel = { viewModel.selectHelpRequest(null) }
                    )
                }
            }
        }
    }
}