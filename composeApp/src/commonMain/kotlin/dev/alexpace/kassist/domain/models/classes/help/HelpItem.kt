package dev.alexpace.kassist.domain.models.classes.help

import dev.alexpace.kassist.domain.models.enums.help.HelpItemType
import dev.alexpace.kassist.domain.models.enums.app.UnitType
import kotlinx.serialization.Serializable

@Serializable
data class HelpItem(
    val id: String,
    val name: String,
    val details: String?,
    val neededQuantity: Int,
    val unit: UnitType,
    val type: HelpItemType,
)
