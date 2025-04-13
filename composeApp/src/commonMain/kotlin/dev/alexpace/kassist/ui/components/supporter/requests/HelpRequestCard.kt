package dev.alexpace.kassist.ui.components.supporter.requests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.victim.HelpRequest

@Composable
fun HelpRequestCard(helpRequest: HelpRequest) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Victim: ${helpRequest.victimName}")
            Text("Address: ${helpRequest.address}")
            Text("Description: ${helpRequest.description}")
            Text("Need Level: ${helpRequest.needLevel.name}")
            Text("Status: ${helpRequest.status.name}")
        }
    }
}