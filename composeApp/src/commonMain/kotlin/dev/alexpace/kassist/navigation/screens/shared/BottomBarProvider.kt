package dev.alexpace.kassist.navigation.screens.shared

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

@Composable
fun BottomBar(tabNavigator: TabNavigator, tabs: List<Tab>) {
    BottomNavigation {
        tabs.forEach { tab ->
            BottomNavigationItem(
                selected = tabNavigator.current.key == tab.key,
                label = { Text(tab.options.title) },
                icon = { Icon(painter = tab.options.icon!!, contentDescription = null) },
                onClick = { tabNavigator.current = tab }
            )
        }
    }
}