package com.gufeczek.di.module

import com.gufeczek.api.RiotApi
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

val networkModule = module {
    single { RiotApi() }
    single { provideHttpClient() }
}

private fun provideHttpClient() = HttpClient(CIO) {
    val env: Dotenv by inject(Dotenv::class.java)

    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
    defaultRequest {
        header("X-Riot-Token", env["RIOT_GAMES_API_KEY"])
    }
}
