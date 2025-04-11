package dev.alexpace.kassist.api.presentation.controllers

import dev.alexpace.kassist.api.application.client.NaturalDisasterClient
import dev.alexpace.kassist.api.domain.enums.AlertLevelTypes
import dev.alexpace.kassist.http.utils.Result
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Opens endpoints. Shouldn't include much business logic.
 */
fun Application.configureRouting(client: NaturalDisasterClient) {
    routing {
        get("/") {
            call.respondText("Ktor: Hello")
        }

        // TODO: Return list of NaturalDisaster or its DTO
        get("/nd/{alertLevel}") {
            val alertLevelStr = call.parameters["alertLevel"]
                ?: return@get call.respondText("Alert level not provided", status = HttpStatusCode.BadRequest)

            val alertLevel = try {
                AlertLevelTypes.valueOf(alertLevelStr.replaceFirstChar { it.uppercase() })
            } catch (e: IllegalArgumentException) {
                return@get call.respondText(
                    "Invalid alert level: $alertLevelStr",
                    status = HttpStatusCode.BadRequest
                )
            }

            when (val result = client.getNaturalDisastersByAlertLevel(alertLevel)) {
                is Result.Success -> call.respondText(result.data.toString())
                is Result.Error -> call.respondText(
                    "Error: ${result.error}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }
    }
}