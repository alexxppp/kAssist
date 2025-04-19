package dev.alexpace.kassist.ui.victim.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.contactTabOptions
import dev.alexpace.kassist.ui.victim.pages.contact.VictimContactPage

object VictimContactTab : Tab {
    @Composable
    override fun Content() {
        VictimContactPage()
    }

    override val options: TabOptions
        @Composable
        get() = contactTabOptions()
}