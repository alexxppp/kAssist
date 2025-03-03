package dev.alexpace.kassist.navigation.screens.supporter

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
import dev.alexpace.kassist.navigation.tabs.supporter.SupporterHomeTab
import dev.alexpace.kassist.navigation.tabs.supporter.SupporterMapTab
import dev.alexpace.kassist.navigation.tabs.supporter.SupporterProfileTab

class SupporterScreen : Screen {
    @Composable
    override fun Content() {

        TabNavigator(
            SupporterHomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(SupporterHomeTab, SupporterMapTab, SupporterProfileTab)
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(it.current.options.title) })
                },
                bottomBar = { SupporterBottomBar() },
                content = {
                    CurrentTab()
                }
            )
        }
    }
}

@Composable
private fun SupporterBottomBar() {
    val tabNavigator = LocalTabNavigator.current
    BottomBar(tabNavigator, listOf(SupporterHomeTab, SupporterMapTab, SupporterProfileTab))
}