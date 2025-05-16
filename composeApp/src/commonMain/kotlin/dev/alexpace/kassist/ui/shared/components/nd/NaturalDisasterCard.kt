package dev.alexpace.kassist.ui.shared.components.nd

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.models.enums.user.UserType
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.ui.shared.components.app.CustomAlertDialog

@Composable
fun NaturalDisasterCard(
    disaster: NaturalDisaster,
    user: User?,
    onConfirmVictim: () -> Unit,
    onConfirmSupporter: () -> Unit,
    onConfirmAdmin: () -> Unit
) {
    val showVictimDialog = remember { mutableStateOf(false) }
    val showSupporterDialog = remember { mutableStateOf(false) }
    val showAdminDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = disaster.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = disaster.description,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Location: ${disaster.country}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4A90E2)
                    )
                )
                Text(
                    text = "Severity: ${disaster.alertLevel}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4A90E2)
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (user?.type == UserType.Neutral && user.role != UserRole.Admin) {
                    // Victim Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF4A90E2),
                                        Color(0xFF357ABD)
                                    )
                                )
                            )
                            .clickable { showVictimDialog.value = true }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "I'm a Victim",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    // Supporter Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF333333),
                                        Color(0xFF1A1A1A)
                                    )
                                )
                            )
                            .clickable { showSupporterDialog.value = true }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "I'm a Supporter",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                } else if (user?.naturalDisaster?.id == disaster.id && user.role != UserRole.Admin) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF333333),
                                        Color(0xFF1A1A1A)
                                    )
                                )
                            )
                            .clickable {
                                if (user.type == UserType.Supporter) {
                                    onConfirmSupporter()
                                } else if (user.type == UserType.Victim) {
                                    onConfirmVictim()
                                }
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Enter",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                } else if (user?.type == UserType.Neutral && user.role == UserRole.Admin) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF4A90E2),
                                        Color(0xFF357ABD)
                                    )
                                )
                            )
                            .clickable {
                                showAdminDialog.value = true
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Administrate",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                } else if (user?.naturalDisaster?.id == disaster.id && user.role == UserRole.Admin) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF333333),
                                        Color(0xFF1A1A1A)
                                    )
                                )
                            )
                            .clickable {
                                onConfirmAdmin()
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Enter",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF4A90E2),
                                        Color(0xFF357ABD)
                                    )
                                )
                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You are already registered in another disaster",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }

    if (showVictimDialog.value) {
        CustomAlertDialog(
            title = "Confirm Action",
            message = "Are you sure you want to register as a victim for ${disaster.name}?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                showVictimDialog.value = false
                onConfirmVictim()
            },
            onDismiss = { showVictimDialog.value = false }
        )
    }

    if (showSupporterDialog.value) {
        CustomAlertDialog(
            title = "Confirm Action",
            message = "Are you sure you want to register as a supporter for ${disaster.name}?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                showSupporterDialog.value = false
                onConfirmSupporter()
            },
            onDismiss = { showSupporterDialog.value = false }
        )
    }

    if (showAdminDialog.value) {
        CustomAlertDialog(
            title = "Confirm Action",
            message = "Are you sure you want to administrate for ${disaster.name}?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                showAdminDialog.value = false
                onConfirmAdmin()
            },
            onDismiss = { showAdminDialog.value = false }
        )
    }
}