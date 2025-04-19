package dev.alexpace.kassist.domain.models.shared.naturalDisaster

import kotlinx.serialization.Serializable

@Serializable
data class NaturalDisaster(
    val id: Int,
    val type: String,

    val name: String,
    val description: String,

    val alertLevel: String,

    val startDate: String,
    val endDate: String,

    val country: String
)