package com.gufeczek.di

import com.gufeczek.di.module.appModule
import com.gufeczek.di.module.clientModule
import com.gufeczek.di.module.daoModule
import com.gufeczek.di.module.networkModule
import com.gufeczek.di.module.persistenceModule
import com.gufeczek.di.module.repositoryModule
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger

fun configureKoin() = startKoin {
    slf4jLogger()
    modules(
        appModule,
        persistenceModule,
        daoModule,
        clientModule,
        networkModule,
        repositoryModule
    )
}
