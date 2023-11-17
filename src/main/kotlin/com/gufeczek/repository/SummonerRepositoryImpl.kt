package com.gufeczek.repository

import com.gufeczek.data.local.SummonerDao
import com.gufeczek.data.local.mapper.asDomain
import com.gufeczek.data.model.Summoner
import com.gufeczek.data.remote.SummonerClient
import com.gufeczek.data.remote.response.SummonerResponse
import com.gufeczek.util.extension.lazyDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SummonerRepositoryImpl : SummonerRepository, KoinComponent {
    private val summonerDao: SummonerDao by inject()
    private val summonerClient: SummonerClient by inject()

    private sealed class SummonerState {
        data class Local(val summoner: Summoner) : SummonerState()
        data class Remote(val summoner: Deferred<SummonerResponse?>) : SummonerState()
        data class LocalAndRemote(val summonerCache: Summoner?, val summonerResponse: Deferred<SummonerResponse?>) : SummonerState()
        data object NotFound : SummonerState()
    }

    override suspend fun getSummoner(summonerName: String, defaultToLocalData: Boolean): Summoner? {
        val summonerCache = summonerDao.select(summonerName = summonerName)?.asDomain()
        val summonerResponse by lazyDeferred(Dispatchers.IO) {
            summonerClient.getSummoner(summonerName = summonerName)
        }

        when {
            summonerCache == null -> SummonerState.Remote(summonerResponse)
            defaultToLocalData -> SummonerState.Local(summonerCache)
            !defaultToLocalData -> SummonerState.LocalAndRemote(summonerCache, summonerResponse)
            else -> SummonerState.NotFound
        }.let { state ->
            when (state) {
                is SummonerState.Local -> return summonerCache
                is SummonerState.Remote -> return summonerResponse.await()?.asDomain().also {
                    insertSummoner(it)
                }

                is SummonerState.LocalAndRemote -> return summonerResponse.await()?.asDomain().also {
                    updateSummoner(it)
                }
                else -> return null
            }
        }
    }

    override suspend fun insertSummoner(summoner: Summoner?): Unit = withContext(Dispatchers.IO) {
        summoner?.let { summonerDao.insert(it.asEntity()) }
    }

    override suspend fun updateSummoner(summoner: Summoner?): Unit = withContext(Dispatchers.IO) {
        summoner?.let { summonerDao.update(it.asEntity()) }
    }
}
