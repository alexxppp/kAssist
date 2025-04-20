package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
private data class SignUpRequest(val email: String, val password: String, val returnSecureToken: Boolean = true)

actual suspend fun registerWithFirebase(email: String, password: String) {

    val apiKey = "AIzaSyDXO57XUvfdnNmWjUaTNuF3k2x9DfhCq2Y"

    val client = createHttpClient(getHttpClient())
    try {
        val response: HttpResponse = client.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey") {
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(email, password))
        }
    } catch (e: Exception) {
        throw Exception("Registration failed: ${e.message}", e)
    } finally {
        client.close()
    }
}