package dev.alexpace.kassist.domain.models.shared

import kotlinx.serialization.Serializable

@Serializable
data class NaturalDisaster(
    val id: Int,
    val name: String,
)
