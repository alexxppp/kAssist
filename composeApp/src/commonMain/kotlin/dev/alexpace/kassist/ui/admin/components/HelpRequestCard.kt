package dev.alexpace.kassist.ui.admin.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.classes.help.HelpItem
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.ui.admin.navigation.screens.RequestReviewScreen

@Composable
fun HelpRequestCard(request: HelpRequest) {
    val navigator = LocalNavigator.currentOrThrow.parent
        ?: throw Exception("Could not find parent navigator")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(0.5.dp, Color(0xFFCCCCCC), RoundedCornerShape(12.dp))
            .clickable { navigator.push(RequestReviewScreen(request)) }
            .semantics { contentDescription = "Help request card for ${request.victimName}" },
        elevation = 2.dp,
        backgroundColor = Color(0xFFF5F7FA)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Text(
                text = request.victimName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            // Request Details
            Text(
                text = "Request ID: ${request.id}",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = "Victim ID: ${request.victimId}",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = "Address: ${request.address}",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
            request.disasterId?.let {
                Text(
                    text = "Disaster ID: $it",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }
            request.description?.let {
                Text(
                    text = "Description: $it",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }
            Text(
                text = "Status: ${request.status.name}",
                fontSize = 14.sp,
                color = Color(0xFFFFA500),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Need Level: ${request.needLevel.name}",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )

            // Items Section
            if (request.items.isNotEmpty()) {
                var expanded by remember { mutableStateOf(false) }
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Items (${request.items.size})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = if (expanded) "Hide" else "Show",
                            fontSize = 14.sp,
                            color = Color(0xFF4A90E2),
                            modifier = Modifier.semantics { contentDescription = if (expanded) "Hide items" else "Show items" }
                        )
                    }
                    if (expanded) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(request.items.filterNotNull()) { item ->
                                HelpItemDisplay(item)
                            }
                        }
                    }
                }
            }
        }
    }
}
