package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class SignUpRequest(val email: String, val password: String, val returnSecureToken: Boolean = true)

@Serializable
private data class SignUpResponse(val idToken: String, val email: String, val refreshToken: String, val expiresIn: String)

@Serializable
private data class ErrorResponse(val error: ErrorDetails)

@Serializable
private data class ErrorDetails(val message: String)

actual suspend fun registerWithFirebase(email: String, password: String) {
    val apiKey = "AIzaSyDXO57XUvfdnNmWjUaTNuF3k2x9DfhCq2Y" // Replace with your actual Firebase Web API Key
    val client = createHttpClient(getHttpClient())
    val json = Json { ignoreUnknownKeys = true }
    try {
        val response: HttpResponse = client.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey") {
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(email, password))
        }
        if (response.status.isSuccess()) {
            // Parse the response to verify registration
            val result = json.decodeFromString<SignUpResponse>(response.bodyAsText())
            // Sign in the user to update the Firebase.auth state
            Firebase.auth.signInWithEmailAndPassword(email, password)
        } else {
            // Handle Firebase-specific errors
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            throw Exception(when (error.error.message) {
                "EMAIL_EXISTS" -> "Email already in use"
                "WEAK_PASSWORD" -> "Password too weak"
                else -> "Registration failed: ${error.error.message}"
            })
        }
    } catch (e: Exception) {
        throw Exception("Registration failed: ${e.message}", e)
    } finally {
        client.close()
    }
}