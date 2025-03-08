package dev.alexpace.kassist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppButton(
    background: Color,
    content: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = background),
        modifier = Modifier.padding(8.dp)
    ) {
        content()
    }
}

@Preview
@Composable
fun previewButton() {
    AppButton(
        background = Color.Red,
        content = { Text("Hello") },
        onClick = { println("Clicked") }
    )
}