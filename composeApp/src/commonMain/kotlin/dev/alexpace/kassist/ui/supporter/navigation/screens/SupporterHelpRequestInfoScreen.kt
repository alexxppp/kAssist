package dev.alexpace.kassist.ui.supporter.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.ui.supporter.pages.helpRequestInfo.SupporterHelpRequestInfoPage

class SupporterHelpRequestInfoScreen(
    val helpRequest: HelpRequest
) : Screen {

    override val key = helpRequest.id

    @Composable
    override fun Content() {
        SupporterHelpRequestInfoPage(helpRequest)
    }
}