package dev.alexpace.kassist.data.utils.helpers

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(val email: String, val password: String, val returnSecureToken: Boolean = true)

@Serializable
data class ErrorResponse(val error: ErrorDetails)

@Serializable
data class ErrorDetails(val message: String)

