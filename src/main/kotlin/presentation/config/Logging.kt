package com.example.presentation.config

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configurationLogging() {
    install(CallLogging) {
        level = Level.DEBUG
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            val params = call.request.uri
            "Status: $status, HTTP method: $httpMethod, params: $params"
        }
    }
}