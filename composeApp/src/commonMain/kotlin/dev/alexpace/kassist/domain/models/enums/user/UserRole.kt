package dev.alexpace.kassist.domain.models.enums.user

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    Basic,
    Admin,
    SuperAdmin,
    Banned
}