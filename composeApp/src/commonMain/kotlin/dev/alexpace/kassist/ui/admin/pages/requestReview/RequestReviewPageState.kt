package dev.alexpace.kassist.ui.admin.pages.requestReview

import dev.alexpace.kassist.domain.models.enums.help.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest

data class RequestReviewPageState(
    val helpRequest: HelpRequest? = null,
    val selectedStatus: RequestStatusTypes? = null,
    val selectedNeedLevel: NeedLevelTypes? = null,
    val victim: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAddressValid: Boolean? = null,
    val suggestedNeedLevel: String? = null
)