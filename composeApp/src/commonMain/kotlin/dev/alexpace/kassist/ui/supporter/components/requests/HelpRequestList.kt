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
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.ui.supporter.pages.help.SupporterHelpPageViewModel
import kotlinx.coroutines.flow.map

@Composable
fun HelpRequestList(viewModel: SupporterHelpPageViewModel) {

    val helpRequests by viewModel.helpRequestRepository.getAll()
        .map { requests -> requests.filter { it.status == RequestStatusTypes.Pending } }
        .collectAsState(initial = emptyList())

    LazyColumn {
        items(helpRequests) { helpRequest ->
            HelpRequestCard(
                helpRequest = helpRequest,
                onClick = { viewModel.selectHelpRequest(helpRequest) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}