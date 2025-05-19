package dev.alexpace.kassist.ui.admin.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.admin.pages.usersActivity.UsersActivityPage
import dev.alexpace.kassist.ui.shared.navigation.utils.adminUsersActivityTabOptions

object AdminUserActivityTab: Tab {
    @Composable
    override fun Content() {
        UsersActivityPage()
    }

    override val options: TabOptions
        @Composable
        get() = adminUsersActivityTabOptions()
}