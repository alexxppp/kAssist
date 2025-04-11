package dev.alexpace.kassist

import dev.alexpace.kassist.api.application.client.NaturalDisasterClient
import dev.alexpace.kassist.api.application.services.NaturalDisasterService
import dev.alexpace.kassist.api.application.client.RetrofitClient
import dev.alexpace.kassist.api.domain.enums.AlertLevelTypes
import dev.alexpace.kassist.api.presentation.controllers.configureRouting
import dev.alexpace.kassist.http.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
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
    testNaturalDisasters()
}

fun testDIApiCall() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val service: NaturalDisasterService = RetrofitClient.retrofit.create(
                NaturalDisasterService::class.java
            )
            println(service.getAllNaturalDisasters())
        } catch (e: Exception) {
            throw Exception("Error making API call: ${e.message}")
        }
    }
}

fun testNaturalDisasters() {
    val client = NaturalDisasterClient(createHttpClient(OkHttp.create()))

    CoroutineScope(Dispatchers.IO).launch {
        try {
            println(client.getNaturalDisasters(AlertLevelTypes.Orange))
        } catch (e: Exception) {
            throw Exception("Error making API call: ${e.message}")
        }
    }
}