package dev.alexpace.kassist.navigation.tabs.victim

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.navigation.tabs.shared.helpTabOptions
import dev.alexpace.kassist.pages.victim.VictimHelpPage

object VictimHelpTab : Tab {
    @Composable
    override fun Content() {
        VictimHelpPage()
    }

    override val options: TabOptions
        @Composable
        get() = helpTabOptions()
}