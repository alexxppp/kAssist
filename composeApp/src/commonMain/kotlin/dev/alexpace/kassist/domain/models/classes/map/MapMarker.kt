package dev.alexpace.kassist.domain.models.classes.map

data class MapMarker(
    val title: String?,
    val lat: Double,
    val lon: Double,
    val color: Float? = null
)
