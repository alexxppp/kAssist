package dev.alexpace.kassist.ui.admin.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.admin.pages.dashboard.DashboardPage
import dev.alexpace.kassist.ui.shared.navigation.utils.adminModeratorTabOptions

object AdminModeratorTab: Tab {
    @Composable
    override fun Content() {
        DashboardPage()
    }


    override val options: TabOptions
        @Composable
        get() = adminModeratorTabOptions()
}