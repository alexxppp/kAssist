package dev.alexpace.kassist.ui.supporter.pages.helpRequestInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import org.koin.compose.koinInject

@Composable
fun SupporterHelpRequestInfoPage(
    helpRequest: HelpRequest
) {
    LaunchedEffect(Unit) {
        println("SupporterHelpRequestInfoPage composed with helpRequest.id: ${helpRequest.id}")
    }

    // Dependency Injection
    val userRepository = koinInject<UserRepository>()
    val helpProposalRepository = koinInject<HelpProposalRepository>()

    // ViewModel
    val viewModel = viewModel {
        SupporterHelpRequestInfoPageViewModel(userRepository, helpProposalRepository, helpRequest)
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF0F4F8), Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Submit Help Proposal",
                    tint = Color(0xFF4A90E2),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Text(
                    text = "Submit Help Proposal",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Provide details on how you can assist",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Form Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Description Field
                    OutlinedTextField(
                        value = viewModel.description.collectAsState().value,
                        onValueChange = { viewModel.updateDescription(it) },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color(0xFFF5F7FA),
                            focusedBorderColor = Color(0xFF4A90E2),
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            cursorColor = Color(0xFF4A90E2)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    // Estimated Time Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = viewModel.estimatedTimeAmount.collectAsState().value,
                            onValueChange = { viewModel.updateEstimatedTimeAmount(it) },
                            label = { Text("Estimated Time") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = Color(0xFFF5F7FA),
                                focusedBorderColor = Color(0xFF4A90E2),
                                unfocusedBorderColor = Color(0xFFCCCCCC),
                                cursorColor = Color(0xFF4A90E2)
                            )
                        )
                        var expanded by remember { mutableStateOf(false) }
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = viewModel.estimatedTimeUnit.collectAsState().value,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Unit") },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color(0xFFF5F7FA),
                                    focusedBorderColor = Color(0xFF4A90E2),
                                    unfocusedBorderColor = Color(0xFFCCCCCC),
                                    cursorColor = Color(0xFF4A90E2)
                                ),
                                trailingIcon = {
                                    IconButton(onClick = { expanded = true }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown",
                                            tint = Color(0xFF4A90E2)
                                        )
                                    }
                                }
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                val timeUnits = listOf("minutes", "hours", "days")
                                timeUnits.forEach { unit ->
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.updateEstimatedTimeUnit(unit)
                                            expanded = false
                                        }
                                    ) {
                                        Text(
                                            text = unit,
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                color = Color(0xFF333333)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Help Items with Checkboxes
                    helpRequest.items.forEach { item ->
                        if (item != null) {
                            val isSelected by viewModel.selectedItemIds.collectAsState()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isSelected.contains(item.id),
                                    onCheckedChange = { viewModel.toggleItemSelection(item.id) }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(item.name, fontWeight = FontWeight.Bold)
                                    Text("${item.neededQuantity} ${item.unit.name}")
                                    if (item.details != null) {
                                        Text(item.details, style = TextStyle(color = Color.Gray))
                                    }
                                }
                            }
                        }
                    }

                    // Submit Button
                    Button(
                        onClick = {
                            val description = viewModel.description.value
                            val timeAmount = viewModel.estimatedTimeAmount.value
                            val timeUnit = viewModel.estimatedTimeUnit.value
                            val error = viewModel.checkForm(description, timeAmount, timeUnit)
                            if (error == null) {
                                viewModel.submitHelpProposal()
                            } else {
                                println(error) // TODO: Display error to user
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4A90E2),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Submit Proposal",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}