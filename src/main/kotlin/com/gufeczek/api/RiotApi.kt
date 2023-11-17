package com.gufeczek.api

import com.gufeczek.data.remote.response.SummonerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RiotApi : KoinComponent {
    private val client: HttpClient by inject()

    suspend fun fetchSummoner(
        summonerName: String
    ): SummonerResponse? {
        val response = client.get("$BASE_URL/summoner/v4/summoners/by-name/$summonerName")
        return if (response.status == HttpStatusCode.OK) {
            response.body<SummonerResponse>()
        } else {
            null
        }
    }

    companion object {
        private const val BASE_URL = "https://euw1.api.riotgames.com/lol"
        private const val API_KEY = "RGAPI-18ccf730-1234-42bf-9640-7e8dd6023b6e"
    }
}
