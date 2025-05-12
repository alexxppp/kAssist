package dev.alexpace.kassist.ui.shared.pages.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.models.shared.liveChat.LiveChat
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.components.chat.MessageCard
import org.koin.compose.koinInject

@Composable
fun ChatPage(
    liveChat: LiveChat,
) {
    // DI
    val liveChatRepository = koinInject<LiveChatRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel = viewModel { ChatPageViewModel(liveChatRepository, userRepository) }

    val liveChatState by viewModel.liveChat.collectAsState()
    val receiverName by viewModel.receiverName.collectAsState()

    // Load chat once when the composable is first composed
    LaunchedEffect(liveChat.id) {
        viewModel.loadChat(liveChat.id)
    }

    // UI
    if (liveChatState == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading...", style = TextStyle(fontSize = 16.sp, color = Color.Gray))
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE6F0FA), Color(0xFFFFFFFF))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = receiverName ?: "Chat",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    reverseLayout = true
                ) {
                    items(liveChatState!!.messages.reversed()) { message ->
                        MessageCard(message = message)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var messageText by remember { mutableStateOf("") }

                    BasicTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xFF333333)
                        ),
                        decorationBox = { innerTextField ->
                            if (messageText.isEmpty()) {
                                Text(
                                    text = "Type a message...",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF666666)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF4A90E2))
                            .clickable {
                                if (messageText.isNotBlank()) {
                                    viewModel.sendMessage(liveChat.id, messageText)
                                    messageText = ""
                                }
                            }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Send",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}