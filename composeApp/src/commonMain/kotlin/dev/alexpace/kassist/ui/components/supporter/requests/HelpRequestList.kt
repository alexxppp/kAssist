package dev.alexpace.kassist.ui.components.supporter.requests

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository

@Composable
fun HelpRequestList(repository: HelpRequestRepository) {
    val helpRequests by repository.getHelpRequests().collectAsState(initial = emptyList())

    LazyColumn {
        items(helpRequests) { helpRequest ->
            HelpRequestCard(helpRequest)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

