package dev.alexpace.kassist.api.domain.responses

import dev.alexpace.kassist.api.domain.models.NaturalDisaster
import kotlinx.serialization.Serializable

@Serializable
data class NaturalDisasterResponse(
    val features: List<Feature>
)

// Content of the top level response
@Serializable
data class Feature(
    val geometry: Geometry,
    val properties: NaturalDisaster
)

@Serializable
data class Geometry(
    val coordinates: List<Double>
)