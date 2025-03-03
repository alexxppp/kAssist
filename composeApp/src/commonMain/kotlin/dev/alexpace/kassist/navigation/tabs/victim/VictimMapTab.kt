package dev.alexpace.kassist.navigation.tabs.victim

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.navigation.tabs.shared.mapTabOptions

object VictimMapTab : Tab {
    @Composable
    override fun Content() {
        Text("Map")
    }

    override val options: TabOptions
        @Composable
        get() = mapTabOptions()
}