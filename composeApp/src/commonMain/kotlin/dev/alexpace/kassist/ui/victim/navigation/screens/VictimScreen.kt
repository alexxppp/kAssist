package dev.alexpace.kassist.ui.victim.navigation.screens

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
import dev.alexpace.kassist.ui.shared.utils.theme.Colors
import dev.alexpace.kassist.ui.victim.navigation.tabs.VictimContactTab
import dev.alexpace.kassist.ui.victim.navigation.tabs.VictimHelpTab
import dev.alexpace.kassist.ui.victim.navigation.tabs.VictimHomeTab

class VictimScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(
            VictimHomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(VictimHomeTab, VictimContactTab, VictimHelpTab)
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
                bottomBar = {
                    BottomBar(
                        LocalTabNavigator.current,
                        listOf(VictimHomeTab, VictimContactTab, VictimHelpTab)
                    )
                },
                content = { CurrentTab() },
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