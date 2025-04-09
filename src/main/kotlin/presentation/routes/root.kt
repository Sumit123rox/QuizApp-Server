package com.example.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.root() {
    get("/") {
        call.respondText { "Hello World!" }
    }
}