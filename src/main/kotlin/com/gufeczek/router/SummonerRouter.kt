package com.gufeczek.router

import com.gufeczek.repository.SummonerRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.summonerApi() {
    val repository: SummonerRepository by inject()

    route("/summoner") {
        get("/{summonerName}") {
            val summonerName = call.parameters["summonerName"].toString()
            val defaultToLocalData = call.parameters["defaultToLocalData"]?.toBoolean() ?: true

            repository.getSummoner(summonerName = summonerName, defaultToLocalData)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: run {
                call.respond(HttpStatusCode.NotFound, "No summoner found with name: $summonerName")
            }
        }
    }
}
