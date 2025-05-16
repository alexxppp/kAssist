package dev.alexpace.kassist.ui.victim.components.proposals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal

@Composable
fun ProposalList(
    helpProposals: List<HelpProposal?>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(helpProposals, key = { it?.id ?: "" }) { helpProposal ->
            helpProposal?.let {
                ProposalCard(it)
            }
        }
    }
}