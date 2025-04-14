package dev.alexpace.kassist.ui.shared.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun HomePage(navigator: Navigator) {
    val viewModel: HomePageViewModel = viewModel { HomePageViewModel() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Navigation buttons
            Text("Home page")
            Button(onClick = { viewModel.navigateToVictimScreen(navigator) }) {
                Text("Go to victim screen")
            }
            Button(onClick = { viewModel.navigateToSupporterScreen(navigator) }) {
                Text("Go to supporter screen")
            }
        }
    }
}