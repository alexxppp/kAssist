package dev.alexpace.kassist.ui.supporter.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.homeTabOptions
import dev.alexpace.kassist.ui.supporter.pages.home.SupporterHomePage

object SupporterHomeTab : Tab {
    @Composable
    override fun Content() {
        SupporterHomePage()
    }

    override val options: TabOptions
        @Composable
        get() = homeTabOptions()
}
