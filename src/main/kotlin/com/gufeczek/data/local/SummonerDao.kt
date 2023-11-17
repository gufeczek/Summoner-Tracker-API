package com.gufeczek.data.local

import com.gufeczek.SummonerEntity
import com.gufeczek.SummonerQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SummonerDao : KoinComponent {
    private val query: SummonerQueries by inject()

    suspend fun select(summonerName: String): SummonerEntity? = withContext(Dispatchers.IO) {
        query.transactionWithResult {
            query.select(summonerName = summonerName).executeAsOneOrNull()
        }
    }

    suspend fun insert(summonerEntity: SummonerEntity) = withContext(Dispatchers.IO) {
        query.transaction {
            query.insert(summonerEntity = summonerEntity)
        }
    }

    suspend fun update(summonerEntity: SummonerEntity) = withContext(Dispatchers.IO) {
        updateFields(summonerEntity)
    }

    private fun updateFields(summonerEntity: SummonerEntity) {
        query.update(
            id = summonerEntity.id,
            summoner_name = summonerEntity.summoner_name,
            summoner_level = summonerEntity.summoner_level,
            profile_icon_id = summonerEntity.profile_icon_id,
            revision_date = summonerEntity.revision_date
        )
    }
}
