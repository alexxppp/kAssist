package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.ui.shared.pages.news.NewsPage

class NewsScreen: Screen {

    // Content
    @Composable
    override fun Content() {
        NewsPage()
    }
}