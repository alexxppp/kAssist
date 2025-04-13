package dev.alexpace.kassist.ui.components.victim.requests
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
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
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import kotlin.random.Random

@Composable
fun HelpRequestForm(onSubmit: (HelpRequest) -> Unit) {
    // State for form fields
    var victimId by remember { mutableStateOf("") }
    var victimName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNeedLevel by remember { mutableStateOf(NeedLevelTypes.Moderate) }
    var selectedStatus by remember { mutableStateOf(RequestStatusTypes.Pending) }

    Column {
        // Victim ID
        TextField(
            value = victimId,
            onValueChange = { victimId = it },
            label = { Text("Victim ID") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Victim Name
        TextField(
            value = victimName,
            onValueChange = { victimName = it },
            label = { Text("Victim Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Address
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Description
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Need Level selection with radio buttons
        Text("Need Level")
        Row {
            NeedLevelTypes.values().forEach { level ->
                Row {
                    RadioButton(
                        selected = selectedNeedLevel == level,
                        onClick = { selectedNeedLevel = level }
                    )
                    Text(level.name)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Status selection with radio buttons
        Text("Status")
        Row {
            RequestStatusTypes.values().forEach { status ->
                Row {
                    RadioButton(
                        selected = selectedStatus == status,
                        onClick = { selectedStatus = status }
                    )
                    Text(status.name)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Submit button
        Button(onClick = {
            val newHelpRequest = HelpRequest(
                id = Random.nextInt(999999999).toString(),
                victimId = victimId,
                victimName = victimName,
                address = address,
                description = description,
                needLevel = selectedNeedLevel,
                status = selectedStatus
            )
            onSubmit(newHelpRequest)
        }) {
            Text("Submit")
        }
    }
}