package dev.alexpace.kassist.ui.pages.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.data.repositoriesImpl.EmergencyPlanRepositoryImpl
import dev.alexpace.kassist.domain.models.EmergencyPlan
import dev.alexpace.kassist.domain.models.enums.AlertLevelTypes
import dev.alexpace.kassist.domain.models.enums.EmergencyTypes
import dev.alexpace.kassist.ui.navigation.screens.supporter.SupporterScreen
import dev.alexpace.kassist.ui.navigation.screens.victim.VictimScreen
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HomePage(navigator: Navigator, emergencyPlanRepository: EmergencyPlanRepositoryImpl) {

    val scope = rememberCoroutineScope()
    val emergencyPlans by emergencyPlanRepository.getEmergencyPlans().collectAsState(emptyList())

    HomePageContent(
        navigator,
        emergencyPlans = emergencyPlans,
        addEmergencyPlan = { scope.launch { emergencyPlanRepository.addEmergencyPlan(it) } },
        updateEmergencyPlan = { scope.launch { emergencyPlanRepository.updateEmergencyPlan(it) } },
        deleteEmergencyPlan = { scope.launch { emergencyPlanRepository.deleteEmergencyPlan(it) } },
    )
}


@Composable
fun HomePageContent(
    navigator: Navigator,
    emergencyPlans: List<EmergencyPlan>,
    addEmergencyPlan: (EmergencyPlan) -> Unit,
    updateEmergencyPlan: (EmergencyPlan) -> Unit,
    deleteEmergencyPlan: (EmergencyPlan) -> Unit,
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedEmergencyPlan by remember { mutableStateOf<EmergencyPlan?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Navigation buttons
            Text("Initial screen")
            Button(onClick = { navigator.push(VictimScreen()) }) {
                Text("Go to victim screen")
            }
            Button(onClick = { navigator.push(SupporterScreen()) }) {
                Text("Go to supporter screen")
            }
            Button(onClick = { showAddDialog = true }) {
                Text("Add Emergency Plan")
            }

            // Emergency plans list
            if (emergencyPlans.isEmpty()) {
                Text("No emergency plans available.", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(emergencyPlans) { plan ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedEmergencyPlan = plan }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(plan.name)
                                    Text(plan.emergencyType.toString())
                                }
                                Button(onClick = {
                                    selectedEmergencyPlan = plan
                                    showEditDialog = true
                                }) {
                                    Text("Edit")
                                }
                                Button(onClick = { deleteEmergencyPlan(plan) }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add Dialog
        if (showAddDialog) {
            EmergencyPlanFormDialog(
                onDismiss = { showAddDialog = false },
                onSave = { newPlan ->
                    addEmergencyPlan(newPlan)
                    showAddDialog = false
                }
            )
        }

        // Edit Dialog
        if (showEditDialog && selectedEmergencyPlan != null) {
            EmergencyPlanFormDialog(
                initialPlan = selectedEmergencyPlan,
                onDismiss = { showEditDialog = false },
                onSave = { updatedPlan ->
                    updateEmergencyPlan(updatedPlan)
                    showEditDialog = false
                }
            )
        }

        // Details Dialog (optional for testing)
        if (selectedEmergencyPlan != null && !showEditDialog) {
            Dialog(onDismissRequest = { selectedEmergencyPlan = null }) {
                Card(modifier = Modifier.padding(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ID: ${selectedEmergencyPlan?.id}")
                        Text("Name: ${selectedEmergencyPlan?.name}")
                        Text("Description: ${selectedEmergencyPlan?.description}")
                        Text("Alert Level: ${selectedEmergencyPlan?.alertLevel}")
                        Text("Emergency Type: ${selectedEmergencyPlan?.emergencyType}")
                        Button(onClick = { selectedEmergencyPlan = null }) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyPlanFormDialog(
    initialPlan: EmergencyPlan? = null,
    onDismiss: () -> Unit,
    onSave: (EmergencyPlan) -> Unit
) {
    var name by remember { mutableStateOf(initialPlan?.name ?: "") }
    var description by remember { mutableStateOf(initialPlan?.description ?: "") }
    var alertLevel by remember { mutableStateOf(initialPlan?.alertLevel ?: AlertLevelTypes.Orange) }
    var emergencyType by remember { mutableStateOf(initialPlan?.emergencyType ?: EmergencyTypes.Flood) }

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(if (initialPlan == null) "Add Emergency Plan" else "Edit Emergency Plan")
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })

                // Alert Level Dropdown
                var expandedAlert by remember { mutableStateOf(false) }
                Text("Alert Level: $alertLevel")
                Button(onClick = { expandedAlert = true }) {
                    Text("Select Alert Level")
                }
                DropdownMenu(expanded = expandedAlert, onDismissRequest = { expandedAlert = false }) {
                    AlertLevelTypes.values().forEach { level ->
                        DropdownMenuItem(onClick = {
                            alertLevel = level
                            expandedAlert = false
                        }) {
                            Text(level.toString())
                        }
                    }
                }

                // Emergency Type Dropdown
                var expandedType by remember { mutableStateOf(false) }
                Text("Emergency Type: $emergencyType")
                Button(onClick = { expandedType = true }) {
                    Text("Select Emergency Type")
                }
                DropdownMenu(expanded = expandedType, onDismissRequest = { expandedType = false }) {
                    EmergencyTypes.entries.forEach { type ->
                        DropdownMenuItem(onClick = {
                            emergencyType = type
                            expandedType = false
                        }) {
                            Text(type.toString())
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        val plan = EmergencyPlan(
                            id = initialPlan?.id ?: Random.nextInt(999999).toString(),
                            name = name,
                            description = description,
                            alertLevel = alertLevel,
                            emergencyType = emergencyType
                        )
                        onSave(plan)
                    }) {
                        Text(if (initialPlan == null) "Add" else "Update")
                    }
                }
            }
        }
    }
}