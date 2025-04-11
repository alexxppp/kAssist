package dev.alexpace.kassist.api.domain.models

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

/**
 * Natural Disaster object
 */
@Serializable
data class NaturalDisaster(
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
