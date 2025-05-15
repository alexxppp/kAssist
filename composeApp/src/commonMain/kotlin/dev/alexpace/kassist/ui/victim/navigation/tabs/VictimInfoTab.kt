package dev.alexpace.kassist.ui.victim.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.infoTabOptions
import dev.alexpace.kassist.ui.shared.pages.news.NewsPage

object VictimInfoTab: Tab {
    @Composable
    override fun Content() {
        NewsPage()
    }

    override val options: TabOptions
        @Composable
        get() = infoTabOptions()
}