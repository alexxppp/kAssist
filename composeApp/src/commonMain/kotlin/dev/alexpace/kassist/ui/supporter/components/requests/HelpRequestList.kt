package dev.alexpace.kassist.ui.supporter.components.requests

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.ui.components.supporter.requests.HelpProposalForm
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HelpRequestList(
    helpRequestRepository: HelpRequestRepository,
    helpProposalRepository: HelpProposalRepositoryImpl,
    currentSupporterId: String
) {
    val helpRequests by helpRequestRepository.getHelpRequests().collectAsState(initial = emptyList())
    var selectedHelpRequest by remember { mutableStateOf<HelpRequest?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        items(helpRequests) { helpRequest ->
            HelpRequestCard(
                helpRequest = helpRequest,
                onClick = { selectedHelpRequest = helpRequest }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    if (selectedHelpRequest != null) {
        Dialog(onDismissRequest = { selectedHelpRequest = null }) {
            HelpProposalForm(
                helpRequest = selectedHelpRequest!!,
                onSubmit = { content ->
                    val newId = Random.nextInt(999999999).toString()
                    val helpProposal = HelpProposal(
                        id = newId,
                        supporterId = currentSupporterId,
                        helpRequestId = selectedHelpRequest!!.id,
                        victimId = selectedHelpRequest!!.victimId,
                        content = content,
                        status = RequestStatusTypes.Pending
                    )
                    coroutineScope.launch {
                        helpProposalRepository.addHelpProposal(helpProposal)
                    }
                    selectedHelpRequest = null
                },
                onCancel = { selectedHelpRequest = null }
            )
        }
    }
}