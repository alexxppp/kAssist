package dev.alexpace.kassist.domain.models.enums.app

import kotlinx.serialization.Serializable

@Serializable
enum class UnitType {
    Kilogram,
    Gram,
    Liter,
    Milliliter,
    Piece,
    Hours,
    Minutes,
    Days
}