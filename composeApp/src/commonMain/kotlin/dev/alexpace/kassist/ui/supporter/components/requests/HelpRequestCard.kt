package dev.alexpace.kassist.ui.supporter.components.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import dev.alexpace.kassist.domain.models.victim.HelpRequest

@Composable
fun HelpRequestCard(helpRequest: HelpRequest, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Victim: ${helpRequest.victimName}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Address: ${helpRequest.address}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${helpRequest.description}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Need Level: ${helpRequest.needLevel.name}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${helpRequest.status.name}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666)
                )
            )
        }
    }
}