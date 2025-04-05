package dev.alexpace.kassist.ui.pages.supporter

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.alexpace.kassist.ui.components.AppButton
import dev.alexpace.kassist.ui.components.HomePageLayout

@Composable
fun SupporterHomePage() {

    HomePageLayout {
        AppButton(Color(0xFF0000), { Text("Supporter Home") }) {
            {}
        }
    }

}