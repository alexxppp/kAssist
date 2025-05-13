package dev.alexpace.kassist.ui.admin.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.ui.admin.pages.requestReview.RequestReviewPage

class RequestReviewScreen(
    val helpRequest: HelpRequest
): Screen {
    @Composable
    override fun Content() {
        RequestReviewPage(helpRequest)
    }
}