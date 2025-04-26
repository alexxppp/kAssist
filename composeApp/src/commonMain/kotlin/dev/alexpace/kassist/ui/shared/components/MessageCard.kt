package dev.alexpace.kassist.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun MessageCard(
    message: ChatMessage
) {
    val currentUserId = Firebase.auth.currentUser?.uid ?: ""

    fun isFromCurrentUser(): Boolean {
        return message.senderId == currentUserId
    }

    val alignment = if (isFromCurrentUser()) Alignment.End else Alignment.Start
    val backgroundColor = if (isFromCurrentUser()) Color(0xFF4A90E2) else Color(0xFFE6F0FA)
    val textColor = if (isFromCurrentUser()) Color.White else Color(0xFF333333)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (isFromCurrentUser()) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .weight(0.8f, fill = false)
        ) {
            Text(
                text = message.content,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = textColor
                )
            )
        }
    }
}