package com.example.presentation.util

import com.example.domain.util.DataError
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

suspend fun RoutingContext.respondWithError(error: DataError) {
    when (error) {
        is DataError.Database -> {
            call.respond(message = "Database error occurred", status = HttpStatusCode.InternalServerError)
        }

        is DataError.NotFound -> {
            call.respond(message = "Resource not Found", status = HttpStatusCode.NotFound)
        }

        is DataError.Unknown -> {
            call.respond(message = "Invalid data provided", status = HttpStatusCode.BadRequest)
        }

        is DataError.Validation -> {
            call.respond(message = "Unknown error occurred", status = HttpStatusCode.InternalServerError)
        }
    }
}