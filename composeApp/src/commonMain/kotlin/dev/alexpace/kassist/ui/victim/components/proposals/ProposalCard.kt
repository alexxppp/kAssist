package dev.alexpace.kassist.ui.victim.components.proposals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes

@Composable
fun ProposalCard(helpProposal: HelpProposal, helpRequest: HelpRequest) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display the HelpRequest description as context
            Text(
                text = "Proposal for your request: ${helpRequest.description}",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Display the content of the HelpProposal
            Text(
                text = "Content: ${helpProposal.content}",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Determine color based on proposal status
            val statusColor = when (helpProposal.status) {
                RequestStatusTypes.Pending -> Color.Gray
                RequestStatusTypes.Accepted -> Color.Green
                RequestStatusTypes.Declined -> Color.Red
                else -> Color.Black // Default color for unhandled statuses
            }

            // Display the status of the proposal
            Text(
                text = "Status: ${helpProposal.status.name}",
                style = MaterialTheme.typography.body2,
                color = statusColor
            )
        }
    }
}