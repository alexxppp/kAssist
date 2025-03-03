package dev.alexpace.kassist.navigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        // Declaring non-nullable navigator
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text("Initial screen")

                Button(
                    onClick ={ navigator.push(VictimScreen()) }
                ) {
                    Text("Go to victim screen")
                }

                Button(
                    onClick = { navigator.push(SupporterScreen()) }
                ) {
                    Text("Go to supporter screen")
                }
            }
        }
    }
}