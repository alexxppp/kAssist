package dev.alexpace.kassist

import dev.alexpace.kassist.api.application.services.NaturalDisasterService
import dev.alexpace.kassist.api.application.client.RetrofitClient
import dev.alexpace.kassist.api.presentation.controllers.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    testDIApiCall()
}

fun testDIApiCall() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val service: NaturalDisasterService = RetrofitClient.retrofit.create(
                NaturalDisasterService::class.java
            )
            println(service.getAllNaturalDisasters().features)
        } catch (e: Exception) {
            throw Exception("Error making API call: ${e.message}")
        }
    }
}
