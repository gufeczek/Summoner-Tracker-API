package com.gufeczek.data.remote.response

import com.gufeczek.data.model.Summoner
import kotlinx.serialization.Serializable

@Serializable
data class SummonerResponse(
    val id: String,
    val accountId: String,
    val puuid: String,
    val name: String,
    val profileIconId: Int,
    val revisionDate: Long,
    val summonerLevel: Long
) {
    fun asDomain() = Summoner(
        id = this.id,
        summonerName = this.name,
        summonerLevel = this.summonerLevel.toInt(),
        profileIconId = this.profileIconId,
        revisionDate = this.revisionDate
    )
}
