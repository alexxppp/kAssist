package dev.alexpace.kassist.domain.models.classes.user

import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.models.enums.user.UserType
import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val email: String,
    val name: String,
    val phoneNumber: String?,
    val naturalDisaster: NaturalDisaster? = null,
    val type: UserType = UserType.Neutral,
    val role: UserRole = UserRole.Basic,
    val score: Int = 0,
    val fcmToken: String? = null
)