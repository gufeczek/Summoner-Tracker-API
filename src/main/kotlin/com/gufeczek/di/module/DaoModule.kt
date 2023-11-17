package com.gufeczek.di.module

import com.gufeczek.data.local.SummonerDao
import org.koin.dsl.module

val daoModule = module {
    single { SummonerDao() }
}
