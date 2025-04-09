package com.example

import com.example.presentation.config.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configurationLogging()
    configationSerialization()
    configurationRouting()
    configureValidation()
    configureStatusPages()
}
