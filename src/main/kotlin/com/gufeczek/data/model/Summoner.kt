package com.gufeczek.data.model

import com.gufeczek.SummonerEntity
import kotlinx.serialization.Serializable

@Serializable
data class Summoner(
    val id: String,
    val summonerName: String,
    val summonerLevel: Int,
    val profileIconId: Int,
    val revisionDate: Long
) {
    fun asEntity(): SummonerEntity = SummonerEntity(
        id = this.id,
        summoner_name = this.summonerName,
        summoner_level = this.summonerLevel,
        profile_icon_id = this.profileIconId,
        revision_date = this.revisionDate
    )
}
