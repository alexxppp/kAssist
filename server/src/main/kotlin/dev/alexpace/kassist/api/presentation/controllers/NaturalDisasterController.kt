package dev.alexpace.kassist.api.presentation.controllers

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Opens endpoints. Shouldn't include much business logic.
 */
fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor: Hello")
        }

        // TODO: Return list of NaturalDisaster or its DTO
        get("/nd") {
        }
    }
}