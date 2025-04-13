package dev.alexpace.kassist.domain.models.shared

import kotlinx.serialization.Serializable

@Serializable
data class FormData(
    val name: String,
    val email: String,
    val phone: String
)