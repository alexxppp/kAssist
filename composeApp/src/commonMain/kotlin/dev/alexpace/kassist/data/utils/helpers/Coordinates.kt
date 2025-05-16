package dev.alexpace.kassist.data.utils.helpers

import dev.alexpace.kassist.domain.models.classes.map.Coordinates
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Checks if two coordinates are within a specified radius of each other.
 * @param coord1 First coordinate (latitude, longitude in degrees).
 * @param coord2 Second coordinate (latitude, longitude in degrees).
 * @param radius Radius in kilometers.
 * @param earthRadius Earth's radius in kilometers (default: 6371.0).
 * @return True if the coordinates are within the radius, false otherwise.
 */
fun areCoordinatesWithinRadius(
    coord1: Coordinates,
    coord2: Coordinates,
    radius: Double,
    earthRadius: Double = 6371.0
): Boolean {
    // Convert degrees to radians
    val lat1 = coord1.latitude * (PI / 180)
    val lon1 = coord1.longitude * (PI / 180)
    val lat2 = coord2.latitude * (PI / 180)
    val lon2 = coord2.longitude * (PI / 180)

    // Differences
    val deltaLat = lat2 - lat1
    val deltaLon = lon2 - lon1

    // Haversine formula
    val a = sin(deltaLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(deltaLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = earthRadius * c

    // Check if distance is within radius
    return distance <= radius
}