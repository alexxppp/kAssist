package dev.alexpace.kassist.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.shared.liveChat.ChatMessage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun MessageCard(
    message: ChatMessage
) {
    val currentUserId = Firebase.auth.currentUser?.uid ?: ""

    fun isFromCurrentUser(): Boolean {
        return message.senderId == currentUserId
    }

    val backgroundColor = if (isFromCurrentUser()) Color(0xFF4A90E2) else Color(0xFFE6F0FA)
    val textColor = if (isFromCurrentUser()) Color.White else Color(0xFF333333)

    // Format timestamp
    val instant = Instant.fromEpochMilliseconds(message.timestamp)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedTime = "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (isFromCurrentUser()) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = message.content,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = textColor
                ),
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedTime,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = textColor.copy(alpha = 0.7f)
                    )
                )
                if (isFromCurrentUser()) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = if (message.seen) "Message seen" else "Message sent",
                        tint = if (message.seen) Color(0xFF4CAF50) else Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}