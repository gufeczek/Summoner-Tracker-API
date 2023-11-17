package com.gufeczek

import com.gufeczek.di.configureKoin
import com.gufeczek.plugin.configureHTTP
import com.gufeczek.plugin.configureRouting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureRouting()
    configureKoin()

    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
}
