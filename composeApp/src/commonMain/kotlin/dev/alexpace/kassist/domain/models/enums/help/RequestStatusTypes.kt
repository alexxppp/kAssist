package dev.alexpace.kassist.domain.models.enums.help

import kotlinx.serialization.Serializable

@Serializable
enum class RequestStatusTypes {
    NotAssigned,
    Accepted,
    Pending,
    Declined
}