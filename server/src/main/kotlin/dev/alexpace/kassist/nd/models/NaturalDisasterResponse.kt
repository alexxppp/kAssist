package dev.alexpace.kassist.nd.models

import com.squareup.moshi.Json

// Top level response
data class NaturalDisasterResponse(
    val features: List<Feature>
)

// Content of the top level response
data class Feature(
    val geometry: Geometry,
    val properties: DisasterProperties
)

data class Geometry(
    val coordinates: List<Double>
)

data class DisasterProperties(
    @Json(name = "eventid")
    val id: Int,

    @Json(name = "eventtype")
    val type: String,

    val name: String,
    val description: String,

    @Json(name = "alertlevel")
    val alertLevel: String,

    @Json(name = "fromdate")
    val startDate: String,

    @Json(name = "todate")
    val endDate: String,

    val country: String
)

