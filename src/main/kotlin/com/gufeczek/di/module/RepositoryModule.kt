package com.gufeczek.di.module

import com.gufeczek.repository.SummonerRepository
import com.gufeczek.repository.SummonerRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SummonerRepository> { SummonerRepositoryImpl() }
}
