package dev.alexpace.kassist.ui.victim.components.proposals

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.supporter.HelpProposal

@Composable
fun ProposalList(helpProposals: List<HelpProposal?>) {

    LazyColumn {
        items(helpProposals) { helpProposal ->
            helpProposal?.let {
                ProposalCard(it)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}