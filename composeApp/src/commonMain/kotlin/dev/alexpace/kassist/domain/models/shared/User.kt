package dev.alexpace.kassist.domain.models.shared

import dev.alexpace.kassist.domain.models.enums.UserType
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val email: String ,
    val name: String,
    val phoneNumber: String?,
    val emergencyPlan: EmergencyPlan? = null,
    val type: UserType = UserType.Neutral
)