package dev.alexpace.kassist.navigation.tabs.supporter

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.navigation.tabs.shared.homeTabOptions
import dev.alexpace.kassist.pages.supporter.SupporterHomePage

object SupporterHomeTab : Tab {
    @Composable
    override fun Content() {
        SupporterHomePage()
    }

    override val options: TabOptions
        @Composable
        get() = homeTabOptions()
}