package dev.alexpace.kassist.data.network.responses

import dev.alexpace.kassist.domain.models.shared.NaturalDisaster
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

@Serializable
data class SeverityData(
    val severity: Double,
    val severitytext: String,
    val severityunit: String
)