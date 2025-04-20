package dev.alexpace.kassist.ui.victim.components.proposals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.ui.victim.navigation.screens.HelpProposalInfoScreen

@Composable
fun ProposalCard(helpProposal: HelpProposal?) {

    val navigator = LocalNavigator.currentOrThrow.parent

    fun navToDetails(proposalId: String) {
        navigator!!.push(HelpProposalInfoScreen(proposalId))
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE6F0FA),
                            Color(0xFFFFFFFF)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Display the content of the HelpProposal
            Text(
                text = helpProposal?.content ?: "No content",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Determine color based on proposal status
            val statusColor = when (helpProposal?.status) {
                RequestStatusTypes.Pending -> Color(0xFF666666)
                RequestStatusTypes.Accepted -> Color(0xFF4CAF50)
                RequestStatusTypes.Declined -> Color(0xFFE57373)
                else -> Color(0xFF333333)
            }

            // Display the status of the proposal
            Text(
                text = "Status: ${helpProposal?.status?.name ?: "Unknown"}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = statusColor
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Button to navigate to info page
            Button(
                onClick = { helpProposal?.id?.let { navToDetails(it) } },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4A90E2),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "View Info",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}
