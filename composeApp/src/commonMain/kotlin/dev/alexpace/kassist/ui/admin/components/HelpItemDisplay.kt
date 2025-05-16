package dev.alexpace.kassist.ui.admin.components

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
import dev.alexpace.kassist.domain.models.classes.help.HelpItem

@Composable
fun HelpItemDisplay(item: HelpItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )
            Text(
                text = "Item ID: ${item.id}",
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = "Quantity: ${item.neededQuantity} ${item.unit.name}",
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = "Type: ${item.type.name}",
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
            item.details?.let {
                Text(
                    text = "Details: $it",
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}