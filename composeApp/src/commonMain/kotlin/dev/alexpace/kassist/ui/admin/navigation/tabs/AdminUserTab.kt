package dev.alexpace.kassist.ui.admin.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.admin.pages.users.UserHandlingPage
import dev.alexpace.kassist.ui.shared.navigation.utils.adminUserTabOptions

object AdminUserTab: Tab {
    @Composable
    override fun Content() {
        UserHandlingPage()
    }


    override val options: TabOptions
        @Composable
        get() = adminUserTabOptions()
}