package dev.alexpace.kassist.domain.models.enums

import kotlinx.serialization.Serializable

@Serializable
enum class RequestStatusTypes {
    NotAssigned,
    Accepted,
    Pending,
    Declined
}