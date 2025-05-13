package dev.alexpace.kassist.ui.admin.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.ui.admin.navigation.screens.RequestReviewScreen

@Composable
fun HelpRequestCard(request: HelpRequest) {
    val navigator = LocalNavigator.currentOrThrow.parent
        ?: throw Exception("Could not find parent navigator")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // External padding for spacing between cards
            .clip(RoundedCornerShape(12.dp))
            .border(0.5.dp, Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .clickable { navigator.push(RequestReviewScreen(request)) },
        elevation = 4.dp, // Add subtle elevation for depth
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp), // Internal padding for content
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = request.victimName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "Address: ${request.address}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Status: ${request.status.name}",
                fontSize = 14.sp,
                color = Color(0xFFFFA500)
            )
            Text(
                text = "Need Level: ${request.needLevel.name}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            if (request.description != null) {
                Text(
                    text = "Description: ${request.description}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}