package dev.alexpace.kassist.ui.victim.components.requests
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
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HelpRequestForm(onSubmit: (HelpRequest) -> Unit) {
    // State for form fields
    var address by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNeedLevel by remember { mutableStateOf(NeedLevelTypes.Moderate) }

    Column {

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
            NeedLevelTypes.entries.forEach { level ->
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

        // Submit button
        Button(onClick = {
            val newHelpRequest = HelpRequest(
                id = Uuid.random().toString(),
                victimId = Uuid.random().toString(), // TODO: Replace for this.id
                victimName = Uuid.random().toString(), // TODO: Replace for this.name
                address = address,
                description = description,
                needLevel = selectedNeedLevel,
                status = RequestStatusTypes.Pending
            )
            onSubmit(newHelpRequest)
        }) {
            Text("Submit")
        }
    }
}