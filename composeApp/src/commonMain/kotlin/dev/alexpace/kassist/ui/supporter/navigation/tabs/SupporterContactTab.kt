package dev.alexpace.kassist.ui.supporter.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.contactTabOptions
import dev.alexpace.kassist.ui.supporter.pages.SupporterContactPage

object SupporterContactTab : Tab {
    @Composable
    override fun Content() {
        SupporterContactPage()
    }

    override val options: TabOptions
        @Composable
        get() = contactTabOptions()
}