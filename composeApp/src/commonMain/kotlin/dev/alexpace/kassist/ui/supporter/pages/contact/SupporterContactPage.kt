package dev.alexpace.kassist.ui.supporter.pages.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.components.chat.ChatCard
import dev.alexpace.kassist.ui.shared.navigation.screens.ChatScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.compose.koinInject

@Composable
fun SupporterContactPage() {
    // Parent screen navigator (NOT tab navigator)
    val navigator =
        LocalNavigator.currentOrThrow.parent ?: throw Exception("No parent navigator found")
    val currentUserId = Firebase.auth.currentUser?.uid
        // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    // DI
    val liveChatRepository = koinInject<LiveChatRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val supporterViewModel = viewModel {
        SupporterContactPageViewModel(liveChatRepository, userRepository, currentUserId)
    }
    val liveChats by supporterViewModel.liveChats.collectAsState()
    val userNames by supporterViewModel.userNames.collectAsState()

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Messages Icon",
                    tint = Color(0xFF4A90E2),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Text(
                    text = "Messages",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your Chats with Victims",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666)
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                if (liveChats.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "No Chats",
                            tint = Color(0xFF4A90E2),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "No Chats Yet",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF666666)
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Chats will appear once a victim approves your help proposal.",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 45.dp)
                    ) {
                        items(liveChats, key = { it.id }) { chat ->
                            val receiverId =
                                if (chat.victimId == currentUserId) chat.supporterId else chat.victimId
                            val receiverName = userNames[receiverId] ?: "Loading..."
                            ChatCard(
                                liveChat = chat,
                                receiverName = receiverName,
                                onChatClick = { _ ->
                                    navigator.push(
                                        ChatScreen(
                                            liveChat = chat
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}