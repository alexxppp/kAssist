package dev.alexpace.kassist.ui.admin.navigation.screens

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.alexpace.kassist.data.utils.helpers.PLATFORM
import dev.alexpace.kassist.ui.admin.navigation.tabs.AdminModeratorTab
import dev.alexpace.kassist.ui.shared.navigation.screens.MapScreen
import dev.alexpace.kassist.ui.shared.navigation.utils.BottomBar
import dev.alexpace.kassist.ui.shared.utils.theme.Colors

class AdminScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(
            AdminModeratorTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(AdminModeratorTab)
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = it.current.options.title, color = Color.White) },
                        backgroundColor = Colors.TopAppBar
                    )
                },
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomBar(
                        LocalTabNavigator.current,
                        listOf(AdminModeratorTab)
                    )
                },
                floatingActionButton = {
                    if (PLATFORM != "Desktop") {
                        FloatingActionButton(
                            onClick = {
                                navigator.push(MapScreen())
                            },
                        ) {
                            Text("Map")
                        }
                    }
                }
            )
        }
    }
}