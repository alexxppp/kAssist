package dev.alexpace.kassist.domain.models.classes.app

import dev.alexpace.kassist.data.network.responses.SeverityData
import dev.alexpace.kassist.domain.models.classes.map.Coordinates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NaturalDisaster(
    @SerialName("eventid") val id: Int,
    @SerialName("eventtype") val type: String,
    @SerialName("alertlevel") val alertLevel: String,
    @SerialName("fromdate") val startDate: String,
    @SerialName("todate") val endDate: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("htmldescription") val htmlDescription: String,
    @SerialName("icon") val icon: String,
    @SerialName("country") val country: String,
    @SerialName("severitydata") val severityData: SeverityData? = null,
    @SerialName("coordinates") val coordinates: Coordinates
)
