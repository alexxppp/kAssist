package dev.alexpace.kassist.data.network.responses.geo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoapifyApiResponse(
    @SerialName("features") val features: List<Feature> = emptyList()
)

@Serializable
data class Feature(
    @SerialName("properties") val properties: Properties
)

@Serializable
data class Properties(
    @SerialName("formatted") val formattedAddress: String? = null,
    @SerialName("rank") val rank: Rank
)

@Serializable
data class Rank(
    @SerialName("confidence") val confidence: Float,
    @SerialName("confidence_city_level") val confidenceCityLevel: Float,
    @SerialName("confidence_street_level") val confidenceStreetLevel: Float,
    @SerialName("confidence_building_level") val confidenceBuildingLevel: Float,
    @SerialName("match_type") val matchType: String
)