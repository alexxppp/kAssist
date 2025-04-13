package dev.alexpace.kassist.ui.victim.navigation.screens

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
import dev.alexpace.kassist.ui.victim.navigation.tabs.VictimHelpTab
import dev.alexpace.kassist.ui.victim.navigation.tabs.VictimHomeTab

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
                bottomBar = { BottomBar(LocalTabNavigator.current, listOf(VictimHomeTab, VictimHelpTab)) },
                content = { CurrentTab() }
            )
        }

    }
}