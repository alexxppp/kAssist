package dev.alexpace.kassist.ui.components.supporter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.domain.models.HelpRequest
import dev.alexpace.kassist.ui.components.shared.AppButton

@Composable
fun RequestCard(
    request: HelpRequest,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(500.dp)
            .height(200.dp)
            .background(Color.White)
    ) {
        // TODO: Make onclick open maps
        Text(request.victimName + ", " + request.address + "\n" + request.description)

        AppButton(
            "Contact with victim",
            onClick = {
                // TODO: Make open victim supporter chat (or victim email if short on time)
            })
    }
}
