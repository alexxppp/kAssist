package dev.alexpace.kassist.ui.supporter.components.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.alexpace.kassist.domain.models.enums.nds.NeedLevelTypes
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest

@Composable
fun HelpRequestCard(helpRequest: HelpRequest, onClick: () -> Unit) {
    val (borderColor, badgeColor) = when (helpRequest.needLevel) {
        NeedLevelTypes.NotAssigned -> Color(0xFF999999) to Color(0xFFF5F5F5)
        NeedLevelTypes.Low -> Color(0xFF4CAF50) to Color(0xFFE8F5E9)
        NeedLevelTypes.Moderate -> Color(0xFFFFCA28) to Color(0xFFFFF8E1)
        NeedLevelTypes.High -> Color(0xFFFF5722) to Color(0xFFFDECEA)
        NeedLevelTypes.VeryHigh -> Color(0xFFD32F2F) to Color(0xFFFCE4EC)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Text(
                    text = helpRequest.victimName,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeColor)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = helpRequest.status.name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Address: ${helpRequest.address}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF555555)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${helpRequest.description}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF555555)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(50))
                        .background(borderColor)
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = "Need Level: ${helpRequest.needLevel.name}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = borderColor
                    )
                )
            }
        }
    }
}