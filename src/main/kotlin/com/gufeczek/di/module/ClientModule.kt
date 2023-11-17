package com.gufeczek.di.module

import com.gufeczek.data.remote.SummonerClient
import org.koin.dsl.module

val clientModule = module {
    single { SummonerClient() }
}
