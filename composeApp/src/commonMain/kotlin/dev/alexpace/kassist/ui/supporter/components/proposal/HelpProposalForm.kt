package dev.alexpace.kassist.ui.components.supporter.requests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.victim.HelpRequest

@Composable
fun HelpProposalForm(
    helpRequest: HelpRequest,
    onSubmit: (String) -> Unit,
    onCancel: () -> Unit
) {
    var content by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Propose help for ${helpRequest.victimName}")
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("How can you help?") },
            singleLine = false,
            minLines = 3,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row {
            Button(onClick = { onSubmit(content) }) {
                Text("Submit")
            }
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}