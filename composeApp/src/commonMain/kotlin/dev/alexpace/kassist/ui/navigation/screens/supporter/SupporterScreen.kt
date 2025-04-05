package dev.alexpace.kassist.ui.navigation.screens.supporter

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
import dev.alexpace.kassist.ui.utils.theme.Colors
import dev.alexpace.kassist.ui.navigation.screens.shared.BottomBar
import dev.alexpace.kassist.ui.navigation.tabs.supporter.SupporterHelpTab
import dev.alexpace.kassist.ui.navigation.tabs.supporter.SupporterHomeTab

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
                bottomBar = { SupporterBottomBar() },
                content = {
                    CurrentTab()
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {  },
                    ) {
                        Text("Map") // TODO: Replace with map icon
                    }
                }
            )
        }
    }
}

@Composable
private fun SupporterBottomBar() {
    val tabNavigator = LocalTabNavigator.current
    BottomBar(tabNavigator, listOf(SupporterHomeTab, SupporterHelpTab))
}