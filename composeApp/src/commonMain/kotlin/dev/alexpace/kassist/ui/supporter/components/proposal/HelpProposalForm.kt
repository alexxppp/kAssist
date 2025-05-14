package dev.alexpace.kassist.ui.supporter.components.proposal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.victim.HelpRequest

@Composable
fun HelpProposalForm(
    helpRequest: HelpRequest,
    onSubmit: (String, String?) -> Unit,
    onCancel: () -> Unit
) {
    var content by remember { mutableStateOf("") }
    var timeAmount by remember { mutableStateOf("") }
    var timeUnit by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val timeUnits = listOf("minutes", "hours", "days")
    val formattedTime = if (timeAmount.isNotBlank() && timeUnit.isNotEmpty()) {
        "$timeAmount $timeUnit"
    } else {
        null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .shadow(2.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Propose Help for ${helpRequest.victimName}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            TextField(
                value = content,
                onValueChange = { content = it },
                label = {
                    Text(
                        "How can you help?",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFE6F0FA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFF4A90E2)
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = timeAmount,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                            timeAmount = newValue
                        }
                    },
                    label = {
                        Text(
                            "Time Needed",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF666666)
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF333333)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFE6F0FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF4A90E2)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE6F0FA))
                        .clickable { isDropdownExpanded = true }
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = timeUnit.ifEmpty { "Select Unit" },
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = if (timeUnit.isEmpty()) Color(0xFF666666) else Color(0xFF333333)
                        )
                    )
                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier
                            .width(140.dp)
                            .background(Color.White)
                    ) {
                        timeUnits.forEach { unit ->
                            DropdownMenuItem(
                                onClick = {
                                    timeUnit = unit
                                    isDropdownExpanded = false
                                }
                            ) {
                                Text(
                                    text = unit,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xFF333333)
                                    )
                                )
                            }
                        }
                    }
                }
            }
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF4A90E2))
                        .clickable {
                            val validationError = checkForm(content, timeAmount, timeUnit)
                            if (validationError == null) {
                                onSubmit(content, formattedTime)
                                content = ""
                                timeAmount = ""
                                timeUnit = ""
                                errorMessage = ""
                            } else {
                                errorMessage = validationError
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF666666))
                        .clickable { onCancel() }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

fun checkForm(content: String, timeAmount: String, timeUnit: String): String? {
    return when {
        content.isBlank() -> "Please describe how you can help."
        timeAmount.isBlank() || timeAmount.toIntOrNull() == null || timeAmount.toInt() < 1 -> "Please enter a valid time amount (1 or more)."
        timeUnit.isEmpty() -> "Please select a time unit."
        else -> null
    }
}