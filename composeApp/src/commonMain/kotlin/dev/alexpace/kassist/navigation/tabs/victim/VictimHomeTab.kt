package dev.alexpace.kassist.navigation.tabs.victim

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.navigation.tabs.shared.homeTabOptions
import dev.alexpace.kassist.pages.victim.VictimHomePage

object VictimHomeTab : Tab {
    @Composable
    override fun Content() {
        VictimHomePage()
    }

    override val options: TabOptions
        @Composable
        get() = homeTabOptions()
}