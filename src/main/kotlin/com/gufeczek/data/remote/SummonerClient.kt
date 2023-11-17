package com.gufeczek.data.remote

import com.gufeczek.api.RiotApi
import com.gufeczek.data.remote.response.SummonerResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SummonerClient : KoinComponent {
    private val riotApi: RiotApi by inject()

    suspend fun getSummoner(summonerName: String): SummonerResponse? {
        return riotApi.fetchSummoner(summonerName = summonerName)
    }
}
