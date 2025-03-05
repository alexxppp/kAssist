package dev.alexpace.kassist.navigation.screens.victim

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.alexpace.kassist.navigation.screens.shared.BottomBar
import dev.alexpace.kassist.navigation.tabs.victim.VictimHelpTab
import dev.alexpace.kassist.navigation.tabs.victim.VictimHomeTab

class VictimScreen : Screen {
    @Composable
    override fun Content() {

        TabNavigator(
            VictimHomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(VictimHomeTab, VictimHelpTab)
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(it.current.options.title) })
                },
                bottomBar = { VictimBottomBar() },
                content = { CurrentTab() }
            )
        }

    }
}

@Composable
private fun VictimBottomBar() {
    val tabNavigator = LocalTabNavigator.current
    BottomBar(tabNavigator, listOf(VictimHomeTab, VictimHelpTab))
}
