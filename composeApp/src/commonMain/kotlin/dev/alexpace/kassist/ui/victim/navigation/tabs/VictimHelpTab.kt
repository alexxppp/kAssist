package dev.alexpace.kassist.ui.victim.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.helpTabOptions
import dev.alexpace.kassist.ui.victim.pages.help.VictimHelpPage

object VictimHelpTab : Tab {
    @Composable
    override fun Content() {
        VictimHelpPage()
    }

    override val options: TabOptions
        @Composable
        get() = helpTabOptions()
}