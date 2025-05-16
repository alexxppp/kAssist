package dev.alexpace.kassist.domain.models.enums.user

import kotlinx.serialization.Serializable

@Serializable
enum class UserType {
    Victim,
    Supporter,
    Neutral,
    Admin
}