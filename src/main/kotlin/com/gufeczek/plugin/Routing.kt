package com.gufeczek.plugin

import com.gufeczek.router.summonerApi
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        summonerApi()
    }
}
