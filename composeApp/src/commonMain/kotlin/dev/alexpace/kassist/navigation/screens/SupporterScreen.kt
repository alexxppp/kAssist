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
                bottomBar = {
                    BottomNavigation {
                        val tabNavigator = LocalTabNavigator.current

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == SupporterHomeTab.key,
                            label = { Text(SupporterHomeTab.options.title) },
                            icon = {
                                Icon(
                                    painter = SupporterHomeTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = SupporterHomeTab }
                        )

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == SupporterMapTab.key,
                            label = { Text(SupporterMapTab.options.title) },
                            icon = {
                                Icon(
                                    painter = SupporterMapTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = SupporterMapTab }
                        )

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == SupporterProfileTab.key,
                            label = { Text(SupporterProfileTab.options.title) },
                            icon = {
                                Icon(
                                    painter = SupporterProfileTab.options.icon!!,
                                    contentDescription = null
                                )
                            },
                            onClick = { tabNavigator.current = SupporterProfileTab }
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