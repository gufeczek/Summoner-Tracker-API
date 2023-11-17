package com.gufeczek.repository

import com.gufeczek.data.model.Summoner
import org.koin.core.component.KoinComponent

interface SummonerRepository : KoinComponent {
    suspend fun getSummoner(summonerName: String, defaultToLocalData: Boolean = true): Summoner?
    suspend fun insertSummoner(summoner: Summoner?)
    suspend fun updateSummoner(summoner: Summoner?)
}
