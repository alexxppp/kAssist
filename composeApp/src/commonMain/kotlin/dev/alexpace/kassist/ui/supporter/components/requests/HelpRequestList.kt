package dev.alexpace.kassist.ui.supporter.components.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.ui.supporter.pages.help.SupporterHelpPageViewModel
import kotlinx.coroutines.flow.map

@Composable
fun HelpRequestList(viewModel: SupporterHelpPageViewModel) {
    val helpRequests by viewModel.helpRequestRepository.getAll()
        .map { requests -> requests.filter { it.status == RequestStatusTypes.Pending } }
        .collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F0FA),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {
        if (helpRequests.isEmpty()) {
            Text("No help requests found", modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(helpRequests) { helpRequest ->
                    HelpRequestCard(
                        helpRequest = helpRequest,
                        onClick = { viewModel.selectHelpRequest(helpRequest) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}