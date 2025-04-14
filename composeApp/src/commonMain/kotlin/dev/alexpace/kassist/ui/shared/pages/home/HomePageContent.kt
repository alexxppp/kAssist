package dev.alexpace.kassist.ui.shared.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.ui.supporter.navigation.screens.SupporterScreen
import dev.alexpace.kassist.ui.victim.navigation.screens.VictimScreen

@Composable
fun HomePageContent(
    navigator: Navigator
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Navigation buttons
            Text("Home page")
            Button(onClick = { navigator.push(VictimScreen()) }) {
                Text("Go to victim screen")
            }
            Button(onClick = { navigator.push(SupporterScreen()) }) {
                Text("Go to supporter screen")
            }
        }
    }
}