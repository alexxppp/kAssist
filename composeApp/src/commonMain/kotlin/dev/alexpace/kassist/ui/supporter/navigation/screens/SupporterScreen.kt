package dev.alexpace.kassist.ui.supporter.navigation.screens

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.alexpace.kassist.ui.shared.navigation.utils.BottomBar
import dev.alexpace.kassist.ui.supporter.navigation.tabs.SupporterHelpTab
import dev.alexpace.kassist.ui.supporter.navigation.tabs.SupporterHomeTab
import dev.alexpace.kassist.ui.shared.utils.theme.Colors

class SupporterScreen : Screen {
    @Composable
    override fun Content() {

        TabNavigator(
            SupporterHomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(SupporterHomeTab, SupporterHelpTab)
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(it.current.options.title) },
                        backgroundColor = Colors.TopAppBar
                    )
                },
                bottomBar = { BottomBar(LocalTabNavigator.current, listOf(SupporterHomeTab, SupporterHelpTab)) },
                content = {
                    CurrentTab()
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { },
                    ) {
                        Text("Map") // TODO: Replace with map icon
                    }
                }
            )
        }
    }
}
