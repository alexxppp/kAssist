package dev.alexpace.kassist.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat

@Composable
fun ChatCard(
    liveChat: LiveChat,
    onChatClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .clickable { onChatClick(liveChat.id) }
            .padding(16.dp)
    ) {
        Text(
            text = "Chat with Victim: ${liveChat.victimId}",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
        )
        Text(
            text = "Disaster: ${liveChat.naturalDisaster.type}",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF666666)
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
        val lastMessage = liveChat.messages.lastOrNull()?.content ?: "No messages yet"
        Text(
            text = "Last: $lastMessage",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF666666)
            ),
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1
        )
    }
}