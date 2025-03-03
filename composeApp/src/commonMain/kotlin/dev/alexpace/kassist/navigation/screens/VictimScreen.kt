package dev.alexpace.kassist.navigation.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.alexpace.kassist.navigation.tabs.victim.VictimHomeTab
import dev.alexpace.kassist.navigation.tabs.victim.VictimMapTab
import dev.alexpace.kassist.navigation.tabs.victim.VictimProfileTab

class VictimScreen : Screen {
    @Composable
    override fun Content() {

        TabNavigator(
            VictimHomeTab,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(VictimHomeTab, VictimMapTab, VictimProfileTab)
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(it.current.options.title) })
                },
                bottomBar = {
                    BottomNavigation {
                        val tabNavigator = LocalTabNavigator.current

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == VictimHomeTab.key,
                            label = { Text(VictimHomeTab.options.title) },
                            icon = {
                                Icon(
                                    painter = VictimHomeTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = VictimHomeTab }
                        )

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == VictimMapTab.key,
                            label = { Text(VictimMapTab.options.title) },
                            icon = {
                                Icon(
                                    painter = VictimMapTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = VictimMapTab }
                        )

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == VictimProfileTab.key,
                            label = { Text(VictimProfileTab.options.title) },
                            icon = {
                                Icon(
                                    painter = VictimProfileTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = VictimProfileTab }
                        )
                    }
                },
                content = {
                    CurrentTab()
                }
            )
        }

    }
}