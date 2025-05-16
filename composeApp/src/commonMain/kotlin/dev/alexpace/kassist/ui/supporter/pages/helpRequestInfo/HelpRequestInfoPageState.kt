package dev.alexpace.kassist.ui.supporter.pages.helpRequestInfo

import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest

data class HelpRequestInfoPageState(
    val helpRequest: HelpRequest?,
    val content: String = "",
    val timeAmount: String = "",
    val timeUnit: String = "",
    val selectedHelpItemIds: Set<String> = emptySet(),
    val errorMessage: String? = null,
    val isSubmitting: Boolean = false
)