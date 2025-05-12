package dev.alexpace.kassist.ui.supporter.navigation.screens

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.alexpace.kassist.ui.shared.navigation.screens.NewsScreen
import dev.alexpace.kassist.ui.shared.navigation.utils.BottomBar
import dev.alexpace.kassist.ui.supporter.navigation.tabs.SupporterHelpTab
import dev.alexpace.kassist.ui.supporter.navigation.tabs.SupporterHomeTab
import dev.alexpace.kassist.ui.shared.utils.theme.Colors
import dev.alexpace.kassist.ui.supporter.navigation.tabs.SupporterContactTab

class SupporterScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(
            SupporterHomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(SupporterHomeTab, SupporterContactTab, SupporterHelpTab)
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
                        listOf(SupporterHomeTab, SupporterContactTab, SupporterHelpTab)
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navigator.push(NewsScreen()) // TODO: Change
                        },
                    ) {
                        Text("Map") // TODO: Replace with map icon
                    }
                }
            )
        }
    }
}
