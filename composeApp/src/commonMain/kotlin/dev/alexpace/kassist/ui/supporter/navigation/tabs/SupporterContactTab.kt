package dev.alexpace.kassist.ui.supporter.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.alexpace.kassist.ui.shared.navigation.utils.contactTabOptions
import dev.alexpace.kassist.ui.supporter.pages.contact.SupporterContactPage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

object SupporterContactTab : Tab {
    @Composable
    override fun Content() {
        val currentUserId = Firebase.auth.currentUser?.uid ?: return
        SupporterContactPage(currentUserId)
    }

    override val options: TabOptions
        @Composable
        get() = contactTabOptions()
}