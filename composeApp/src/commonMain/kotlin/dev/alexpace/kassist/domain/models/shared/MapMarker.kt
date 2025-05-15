package dev.alexpace.kassist.domain.models.shared

data class MapMarker(
    val title: String?,
    val lat: Double,
    val lon: Double,
    val color: Float? = null
)
