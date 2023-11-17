package com.gufeczek.data.local.mapper

import com.gufeczek.SummonerEntity
import com.gufeczek.data.model.Summoner

fun SummonerEntity.asDomain() = Summoner(
    id = this.id,
    summonerName = this.summoner_name,
    summonerLevel = this.summoner_level,
    profileIconId = this.profile_icon_id,
    revisionDate = this.revision_date
)
