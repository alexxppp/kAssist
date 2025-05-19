package dev.alexpace.kassist.ui.admin.pages.usersActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import org.koin.compose.koinInject

@Composable
fun UsersActivityPage() {

    // DI
    val userRepository = koinInject<UserRepository>()
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val helpProposalRepository = koinInject<HelpProposalRepository>()

    // ViewModel
    val viewModel = viewModel {
        UsersActivityViewModel(
            userRepository, helpRequestRepository, helpProposalRepository
        )
    }

    val state by viewModel.state.collectAsState()
    val sortedProposals = state.helpProposals.filterNotNull().sortedByDescending { it.id }


    // UI
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sortedProposals) { proposal ->
            val relatedRequest = state.helpRequests
                .filterNotNull()
                .find { it.id == proposal.helpRequestId }

            HelpProposalRequestItem(
                proposal = proposal,
                request = relatedRequest
            )
        }
    }
}

@Composable
fun HelpProposalRequestItem(
    proposal: HelpProposal,
    request: HelpRequest?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        // Help Proposal Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFBBDEFB)) // Light blue for proposals
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = proposal.supporterName,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1) // Dark blue text
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Proposal: ${proposal.content}",
                color = Color(0xFF0D47A1)
            )
        }

        // Connector Line
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .width(2.dp)
                .height(16.dp)
                .background(Color.Gray)
        )

        // Help Request Card (if exists)
        request?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFFCDD2)) // Light red for requests
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.victimName,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB71C1C) // Dark red text
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Request: ${it.description ?: "No description"}",
                    color = Color(0xFFB71C1C)
                )
            }
        }
    }
}