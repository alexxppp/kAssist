package dev.alexpace.kassist.api.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor: Hello")
        }

        get("/hello") {
            call.respondText("Hello User")
        }
    }
}