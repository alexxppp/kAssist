package dev.alexpace.kassist.ui.shared.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun AppScaffold(
    screen: Screen
) {
    val navigator = LocalNavigator.currentOrThrow
    val canGoBack = navigator.canPop

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("kAssist") },
                navigationIcon = {
                    if (canGoBack) {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize().padding(it)) {
                screen.Content()
            }
        }
    )
}