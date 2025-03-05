package dev.alexpace.kassist.navigation.tabs.supporter

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.navigation.tabs.shared.helpTabOptions
import dev.alexpace.kassist.pages.supporter.SupporterHelpPage

object SupporterHelpTab : Tab {
    @Composable
    override fun Content() {
        SupporterHelpPage()
    }

    override val options: TabOptions
        @Composable
        get() = helpTabOptions()
}