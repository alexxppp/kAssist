package dev.alexpace.kassist.domain.models

data class HelpRequest (
    val Id: Int,
    val victimName: String,
    val address: String,
    val description: String
)