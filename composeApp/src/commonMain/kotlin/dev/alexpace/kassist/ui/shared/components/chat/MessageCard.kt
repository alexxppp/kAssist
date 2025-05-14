package dev.alexpace.kassist.ui.shared.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.shadow
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
    val formattedTime = "${dateTime.hour.toString().padStart(2, '0')}:${
        dateTime.minute.toString().padStart(2, '0')
    }"

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (isFromCurrentUser()) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .shadow(2.dp, RoundedCornerShape(12.dp)).clip(
                    RoundedCornerShape(
                        topStart = if (isFromCurrentUser()) 12.dp else 0.dp,
                        topEnd = 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = if (isFromCurrentUser()) 0.dp else 12.dp
                    )
                )
                .background(backgroundColor)
                .padding(horizontal = 12.dp, vertical = 8.dp)

        ) {
            Column {
                Text(
                    text = message.content,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = textColor
                    )
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp),
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
                            tint = if (message.seen) Color(0xFF4CAF50) else Color.White,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(14.dp)
                        )
                    }
                }
            }
        }
    }
}