package dev.alexpace.kassist

import dev.alexpace.kassist.api.configureRouting
import dev.alexpace.kassist.nd.client.RetrofitClient
import dev.alexpace.kassist.nd.service.NaturalDisastersService
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
    testApiCall()
}

fun testApiCall() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val service: NaturalDisastersService = RetrofitClient.retrofit.create(NaturalDisastersService::class.java)
            println(service.getNaturalDisasters())
        } catch (e: Exception) {
            throw Exception("Error making API call: ${e.message}")
        }
    }
}
