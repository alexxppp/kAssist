package dev.alexpace.kassist.ui.victim.components.proposals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.ui.victim.navigation.screens.HelpProposalInfoScreen

@Composable
fun ProposalCard(helpProposal: HelpProposal) {
    val navigator = LocalNavigator.currentOrThrow.parent

    fun navToDetails(proposal: HelpProposal) {
        navigator!!.push(HelpProposalInfoScreen(proposal))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .border(0.1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = helpProposal.content,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${helpProposal.status.name}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4A90E2)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { navToDetails(helpProposal) },
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