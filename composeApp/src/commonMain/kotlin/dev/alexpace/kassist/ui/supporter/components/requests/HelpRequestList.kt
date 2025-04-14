package dev.alexpace.kassist.ui.supporter.components.requests

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.alexpace.kassist.ui.supporter.pages.help.SupporterHelpPageViewModel
import dev.alexpace.kassist.ui.components.supporter.requests.HelpProposalForm

@Composable
fun HelpRequestList(viewModel: SupporterHelpPageViewModel) {
    val helpRequests by viewModel.helpRequestRepository.getHelpRequests().collectAsState(initial = emptyList())
    val selectedHelpRequest by viewModel.selectedHelpRequest.collectAsState()

    LazyColumn {
        items(helpRequests) { helpRequest ->
            HelpRequestCard(
                helpRequest = helpRequest,
                onClick = { viewModel.selectHelpRequest(helpRequest) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    if (selectedHelpRequest != null) {
        Dialog(onDismissRequest = { viewModel.selectHelpRequest(null) }) {
            HelpProposalForm(
                helpRequest = selectedHelpRequest!!,
                onSubmit = { content ->
                    viewModel.submitHelpProposal(content, selectedHelpRequest!!)
                },
                onCancel = { viewModel.selectHelpRequest(null) }
            )
        }
    }
}