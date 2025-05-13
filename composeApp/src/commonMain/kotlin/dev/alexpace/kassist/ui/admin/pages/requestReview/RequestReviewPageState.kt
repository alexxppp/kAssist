package dev.alexpace.kassist.ui.admin.pages.requestReview

import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest

data class RequestReviewPageState(
    val helpRequest: HelpRequest? = null,
    val selectedStatus: RequestStatusTypes? = null,
    val selectedNeedLevel: NeedLevelTypes? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAddressValid: Boolean? = null
)