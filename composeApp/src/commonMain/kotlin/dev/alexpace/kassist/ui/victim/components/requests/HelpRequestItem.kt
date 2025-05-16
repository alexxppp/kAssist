package dev.alexpace.kassist.ui.victim.components.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.classes.help.HelpItem
import dev.alexpace.kassist.domain.models.enums.app.UnitType
import dev.alexpace.kassist.domain.models.enums.help.HelpItemType

@Composable
fun HelpRequestItem(
    helpItem: HelpItem,
    onUpdate: (HelpItem) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFFF5F7FA),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Name
            OutlinedTextField(
                value = helpItem.name,
                onValueChange = { onUpdate(helpItem.copy(name = it)) },
                label = { Text("Item Name") },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color(0xFFCCCCCC),
                    cursorColor = Color(0xFF4A90E2)
                )
            )

            // Quantity and Unit Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = helpItem.neededQuantity.toString(),
                    onValueChange = {
                        onUpdate(
                            helpItem.copy(
                                neededQuantity = it.toIntOrNull() ?: 0
                            )
                        )
                    },
                    label = { Text("Quantity") },
                    modifier = Modifier
                        .weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        cursorColor = Color(0xFF4A90E2)
                    )
                )
                var expandedUnit by remember { mutableStateOf(false) }
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = helpItem.unit.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Unit") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Color(0xFF4A90E2),
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            cursorColor = Color(0xFF4A90E2)
                        ),
                        trailingIcon = {
                            IconButton(onClick = { expandedUnit = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = Color(0xFF4A90E2)
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expandedUnit,
                        onDismissRequest = { expandedUnit = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        UnitType.entries.forEach { unit ->
                            DropdownMenuItem(
                                onClick = {
                                    onUpdate(helpItem.copy(unit = unit))
                                    expandedUnit = false
                                }
                            ) {
                                Text(
                                    text = unit.name,
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

            // Type and Details Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var expandedType by remember { mutableStateOf(false) }
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = helpItem.type.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Type") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Color(0xFF4A90E2),
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            cursorColor = Color(0xFF4A90E2)
                        ),
                        trailingIcon = {
                            IconButton(onClick = { expandedType = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = Color(0xFF4A90E2)
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expandedType,
                        onDismissRequest = { expandedType = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        HelpItemType.entries.forEach { type ->
                            DropdownMenuItem(
                                onClick = {
                                    onUpdate(helpItem.copy(type = type))
                                    expandedType = false
                                }
                            ) {
                                Text(
                                    text = type.name,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF333333)
                                    )
                                )
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = helpItem.details ?: "",
                    onValueChange = { onUpdate(helpItem.copy(details = it)) },
                    label = { Text("Details (optional)") },
                    modifier = Modifier
                        .weight(1f),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF333333)),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        cursorColor = Color(0xFF4A90E2)
                    )
                )
            }

            // Remove Button
            Button(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.End)
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFE63946),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Remove",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}