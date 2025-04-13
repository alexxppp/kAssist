package dev.alexpace.kassist.ui.victim.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.homeTabOptions
import dev.alexpace.kassist.ui.victim.pages.home.VictimHomePage

object VictimHomeTab : Tab {
    @Composable
    override fun Content() {
        VictimHomePage()
    }

    override val options: TabOptions
        @Composable
        get() = homeTabOptions()
}