package dev.alexpace.kassist.ui.supporter.navigation.tabs

import dev.alexpace.kassist.ui.supporter.pages.help.SupporterHelpPage
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.helpTabOptions

object SupporterHelpTab : Tab {
    @Composable
    override fun Content() {
        SupporterHelpPage()
    }

    override val options: TabOptions
        @Composable
        get() = helpTabOptions()
}