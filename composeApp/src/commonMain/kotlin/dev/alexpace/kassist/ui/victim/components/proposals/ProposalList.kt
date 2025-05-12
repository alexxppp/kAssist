package dev.alexpace.kassist.ui.victim.components.proposals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.supporter.HelpProposal

@Composable
fun ProposalList(
    helpProposals: List<HelpProposal?>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(helpProposals) { helpProposal ->
            helpProposal?.let {
                ProposalCard(it)
            }
        }
    }
}