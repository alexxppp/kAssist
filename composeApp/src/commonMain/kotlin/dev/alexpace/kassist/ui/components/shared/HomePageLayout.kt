package dev.alexpace.kassist.ui.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexpace.kassist.ui.utils.theme.Colors

@Composable
fun HomePageLayout(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Colors.MainBackground)
            .padding(14.dp)
            .fillMaxSize()
    ) {
        content()
    }
}
