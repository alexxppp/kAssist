package dev.alexpace.kassist.ui.victim.components.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.ui.shared.components.InputField
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HelpRequestForm(currentUser: User, onSubmit: (HelpRequest) -> Unit) {
    // State for form fields
    var address by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNeedLevel by remember { mutableStateOf(NeedLevelTypes.Moderate) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F0FA),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Create Help Request",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            )
            Text(
                text = "Fill in the details to request assistance",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Address
            InputField(
                value = address,
                onValueChange = { address = it },
                placeholder = "Address",
                keyboardType = KeyboardType.Text
            )

            // Description
            InputField(
                value = description,
                onValueChange = { description = it },
                placeholder = "Description",
                keyboardType = KeyboardType.Text
            )

            // Need Level selection
            Text(
                text = "Need Level",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
            )
            Box {
                OutlinedTextField(
                    value = selectedNeedLevel.name,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF333333)
                    ),
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .clickable { isDropdownExpanded = !isDropdownExpanded }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = if (isDropdownExpanded) "▲" else "▼",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color(0xFF333333)
                                )
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    NeedLevelTypes.entries.forEach { level ->
                        DropdownMenuItem(
                            onClick = {
                                selectedNeedLevel = level
                                isDropdownExpanded = false
                            }
                        ) {
                            Text(
                                text = level.name,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF333333)
                                )
                            )
                        }
                    }
                }
            }

            // Submit button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF4A90E2))
                    .clickable {
                        val newHelpRequest = HelpRequest(
                            id = Uuid.random().toString(),
                            victimId = currentUser.id,
                            victimName = currentUser.name,
                            address = address,
                            description = description,
                            needLevel = selectedNeedLevel,
                            status = RequestStatusTypes.Pending
                        )
                        onSubmit(newHelpRequest)
                    }
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Submit",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}